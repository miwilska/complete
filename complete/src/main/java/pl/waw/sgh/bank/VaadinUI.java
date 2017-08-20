package pl.waw.sgh.bank;

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

	private final CardRepository accountRepo;

	private final CardPileEditor editor;

	private final CardEditor cardEditor;

	private final Grid grid;

	private final Grid accountGrid;

	private final TextField filter;

	private final Button addNewBtn;

	private final Button addNewDebitAccountBtn;

	private final Button addNewSavingsAccountBtn;

	private HorizontalLayout newAccountsLayout;

	@Autowired
	public VaadinUI(CardPileRepository repo, CardPileEditor editor, CardRepository accountRepo, CardEditor cardEditor) {
		this.repo = repo;
		this.editor = editor;
		this.accountRepo = accountRepo;
		this.cardEditor = cardEditor;
		this.grid = new Grid(CardPile.class);
		this.accountGrid = new Grid(Card.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New pile", FontAwesome.PLUS);
		this.addNewDebitAccountBtn = new Button("New card", FontAwesome.PLUS);
		this.addNewSavingsAccountBtn = new Button("New Savings", FontAwesome.PLUS);
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);

		VerticalLayout customerLayout = new VerticalLayout(grid, editor);

		newAccountsLayout = new HorizontalLayout(addNewDebitAccountBtn, addNewSavingsAccountBtn);

		VerticalLayout accountLayout = new VerticalLayout(accountGrid, newAccountsLayout, cardEditor);

		HorizontalLayout grids = new HorizontalLayout(customerLayout, accountLayout);

		VerticalLayout mainLayout = new VerticalLayout(actions, grids);

		setContent(mainLayout);

		newAccountsLayout.setVisible(false);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(300, Unit.PIXELS);
		grid.setWidth(350, Unit.PIXELS);
		grid.setColumns("customerID", "pileNumber", "pileTyp");

		accountGrid.setHeight(300, Unit.PIXELS);
		accountGrid.setWidth(350, Unit.PIXELS);
		accountGrid.setColumns("cardID", "savings", "balance");

		//accountGrid.getColumn("savings").setRenderer(new ImageRenderer());
		
		filter.setPlaceholder("Filter by pile typs");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected CardPile to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getAllSelectedItems().isEmpty()) {
				editor.setVisible(false);
				listAccounts(null);
			}
			else {
				CardPile selCust = new ArrayList<CardPile>(grid.getSelectedItems()).get(0);
				editor.editCustomer(selCust);
				listAccounts(selCust);
			}
		});


		accountGrid.addSelectionListener(e -> {
			if (e.getAllSelectedItems().isEmpty()) {
				cardEditor.setVisible(false);
			}
			else {
				Card selAcc = new ArrayList<Card>(accountGrid.getSelectedItems()).get(0);
				cardEditor.editAccount(selAcc);
			}
		});

		// Instantiate and edit new CardPile the new button is clicked
		addNewBtn.addClickListener(e -> editor.editCustomer(new CardPile("", "")));

		addNewDebitAccountBtn.addClickListener(e -> cardEditor.editAccount(
				new DebitCards(new ArrayList<CardPile>(grid.getSelectedItems()).get(0))));

		addNewSavingsAccountBtn.addClickListener(e -> cardEditor.editAccount(
				new SavingsCards(new ArrayList<CardPile>(grid.getSelectedItems()).get(0))));

		// Listen changes made by the editor, refresh data from backend
		editor.setChangeHandler(() -> {
			editor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Listen changes made by the editor, refresh data from backend
		cardEditor.setChangeHandler(() -> {
			cardEditor.setVisible(false);
			listAccounts(new ArrayList<CardPile>(grid.getSelectedItems()).get(0));
			//listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
		listAccounts(null);

	}

	// tag::listCustomers[]
	private void listCustomers(String text) {
		if (StringUtils.isEmpty(text)) {
			grid.setDataProvider(
					new ListDataProvider<CardPile>(repo.findAll()));
			newAccountsLayout.setVisible(false);
		}
		else {
			grid.setDataProvider(new ListDataProvider<CardPile>(
					repo.findByPileTypStartsWithIgnoreCase(text)));
			newAccountsLayout.setVisible(false);
		}
	}
	// end::listCustomers[]


	// tag::listAccounts[]
	private void listAccounts(CardPile owner) {
		if (owner==null) {
			accountGrid.setDataProvider(
					new ListDataProvider<Card>(new ArrayList<Card>()));
		}
		else {
			accountGrid.setDataProvider((new ListDataProvider<Card>(
					accountRepo.findByCardPile(owner))));
			newAccountsLayout.setVisible(true);
		}
	}
	// end::listAccounts[]

}
