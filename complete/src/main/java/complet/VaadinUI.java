package complet;

import com.vaadin.data.provider.ListDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	private final CardPileRepository repo;

	private final CardRepository cardRepo;

	private final CardPileEditor editor;

	private final CardEditor cardEditor;

	private final Grid grid;

	private final Grid cardGrid;

	private final TextField filter;

	private final Button addNewBtn;

	private final Button addNewCardToPileBtn;

//	private final Button addNewSavingsAccountBtn;

	private HorizontalLayout newCardsLayout;

	@Autowired
	public VaadinUI(CardPileRepository repo, CardPileEditor editor, CardRepository cardRepo, CardEditor cardEditor) {
		this.repo = repo;
		this.editor = editor;
		this.cardRepo = cardRepo;
		this.cardEditor = cardEditor;
		this.grid = new Grid(CardPile.class);
		this.cardGrid = new Grid(Card.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New pile", FontAwesome.PLUS);
		this.addNewCardToPileBtn = new Button("New card", FontAwesome.PLUS);
	//	this.addNewSavingsAccountBtn = new Button("New Savings", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);

		VerticalLayout customerLayout = new VerticalLayout(grid, editor);

		newCardsLayout = new HorizontalLayout(addNewCardToPileBtn);//, addNewSavingsAccountBtn);

		VerticalLayout accountLayout = new VerticalLayout(cardGrid, newCardsLayout, cardEditor);

		HorizontalLayout grids = new HorizontalLayout(customerLayout, accountLayout);

		VerticalLayout mainLayout = new VerticalLayout(actions, grids);

		setContent(mainLayout);

		newCardsLayout.setVisible(false);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(500, Unit.PIXELS);
		grid.setWidth(850, Unit.PIXELS);
		grid.setColumns("cardPileID", "pileNumber", "pileTyp");

		cardGrid.setHeight(600, Unit.PIXELS);
		cardGrid.setWidth(550, Unit.PIXELS);
		cardGrid.setColumns("cardID", "suit", "face");//, "isFaceUp");
		//cardGrid.setColumns("cardID", "cardPile", "description");//, "isFaceUp");

		//cardGrid.getColumn("savings").setRenderer(new ImageRenderer());
		
		filter.setPlaceholder("Filter by pile typs");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addValueChangeListener(e -> listCardPiles(e.getValue()));

		// Connect selected CardPile to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getAllSelectedItems().isEmpty()) {
				editor.setVisible(false);
				listCards(null);
			}
			else {
				CardPile selCust = new ArrayList<CardPile>(grid.getSelectedItems()).get(0);
				editor.editCardPile(selCust);
				listCards(selCust);
			}
		});


		cardGrid.addSelectionListener(e -> {
			if (e.getAllSelectedItems().isEmpty()) {
				cardEditor.setVisible(false);
			}
			else {
				Card selAcc = new ArrayList<Card>(cardGrid.getSelectedItems()).get(0);
				cardEditor.editAccount(selAcc);
			}
		});

		// Instantiate and edit new CardPile the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCardPile(new CardPile("", "")));

		addNewCardToPileBtn.addClickListener(e -> cardEditor.editAccount(
				new AddingCards(new ArrayList<CardPile>(grid.getSelectedItems()).get(0))));

		//addNewSavingsAccountBtn.addClickListener(e -> cardEditor.editAccount(
	//			new AddingTurnedBackCards(new ArrayList<CardPile>(grid.getSelectedItems()).get(0))));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCardPiles(filter.getValue());
		});

		// Listen changes made by the editor, refresh data from backend
		cardEditor.setChangeHandler(() -> {
			cardEditor.setVisible(false);
			listCards(new ArrayList<CardPile>(grid.getSelectedItems()).get(0));
			//listCardPiles(filter.getValue());
		});

		// Initialize listing
		listCardPiles(null);
		listCards(null);

	}

	// tag::listCardPiles[]
	private void listCardPiles(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setDataProvider(
					new ListDataProvider<CardPile>(repo.findAll()));
			newCardsLayout.setVisible(false);
		}
		else {
			grid.setDataProvider(new ListDataProvider<CardPile>(
					repo.findByPileTypStartsWithIgnoreCase(text)));
			newCardsLayout.setVisible(false);
		}
	}
	// end::listCardPiles[]


	// tag::listCards[]
	private void listCards(CardPile owner) {
		if (owner==null) {
			cardGrid.setDataProvider(
					new ListDataProvider<Card>(new ArrayList<Card>()));
		}
		else {
			cardGrid.setDataProvider((new ListDataProvider<Card>(
					cardRepo.findByCardPile(owner))));
			newCardsLayout.setVisible(true);
		}
	}
	// end::listCards[]

}
