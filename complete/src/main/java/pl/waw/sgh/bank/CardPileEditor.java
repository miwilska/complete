package pl.waw.sgh.bank;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.themes.ValoTheme;

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
public class CardPileEditor extends VerticalLayout {

	private final CardPileRepository repository;

	/**
	 * The currently edited cardPile
	 */
	private CardPile cardPile;

	/* Fields to edit properties in CardPile entity */
	TextField pileNumber = new TextField("Pile number");
	TextField pileTyp = new TextField("Pile typ");
	Binder<CardPile> customerBinder;

	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public CardPileEditor(CardPileRepository repository) {
		this.repository = repository;

		addComponents(pileNumber, pileTyp, actions);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> {
			try {
				customerBinder.writeBean(cardPile);
				repository.save(cardPile);
			} catch (ValidationException ve) {
				Notification.show("Problem validating cardPile " + ve.getMessage());
			}
		});
		delete.addClickListener(e -> repository.delete(cardPile));
		cancel.addClickListener(e -> editCustomer(cardPile));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editCustomer(CardPile c) {
		final boolean persisted = c.getCustomerID() != null;
		if (persisted) {
			// Find fresh entity for editing
			cardPile = repository.findOne(c.getCustomerID());
		}
		else {
			cardPile = c;
		}
		cancel.setVisible(persisted);

		// Bind cardPile properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving

		customerBinder = new Binder<>(CardPile.class);
		customerBinder.bindInstanceFields(this);
		customerBinder.readBean(cardPile);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in pileNumber field automatically
		pileNumber.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
