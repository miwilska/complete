package pl.waw.sgh.bank;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToBigDecimalConverter;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form in
 * multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX. See e.g. AbstractForm in Virin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class CardEditor extends VerticalLayout {

	private final CardRepository repository;

	/**
	 * The currently edited card
	 */
	private Card card;

	/* Fields to edit properties in card entity */
	//TextField customer = new TextField("CardPile");
	TextField balance = new TextField("Balance");
	TextField suit = new TextField("Suit");
	TextField face = new TextField("Face");
	Binder<Card> crdBinder;

	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, delete);

	@Autowired
	public CardEditor(CardRepository repository) {
		this.repository = repository;

		addComponents(suit, face, actions);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {
					try {
						crdBinder.writeBean(card);
						repository.save(card);
					} catch (ValidationException ve) {
						Notification.show("Problem validating card " + ve.getMessage());
					}
		});
		delete.addClickListener(e -> repository.delete(card));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editAccount(Card c) {
		final boolean persisted = c.getCardID() != null;
		if (persisted) {
			// Find fresh entity for editing
			card = repository.findOne(c.getCardID());
		}
		else {
			card = c;
		}

		// Bind card properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		crdBinder = new Binder<>(Card.class);
		crdBinder.forField(balance).withConverter(new StringToBigDecimalConverter("Must be a number"))
				.bind(Card::getBalance, Card::setBalance);
		crdBinder.bindInstanceFields(this);


		crdBinder.readBean(card);


		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in pileNumber field automatically
		//customer.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
