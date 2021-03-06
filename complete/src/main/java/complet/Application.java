package complet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(CardPileRepository repository, CardRepository cardRepository) {
		return (args) -> {
			// save a couple of customers

			CardPile c1 = new CardPile("1 trefl", "wieloznaczne: 1)słabe NT- 12-15 PC (skład bezatutowy oraz trzy czwórki (bez singla trefl), 2)trefl 12-18PC (5+ trefli), 3) silne 19++ PC  skład dowolny");
			CardPile c2 = new CardPile("1 karo", " dwuznaczne: 1) kara (12-18PC 5+ kar) 2) układ 4-4-4-1");
			CardPile c3 = new CardPile("1 kier", "12-15PC 5+ kierów");
			repository.save(c1);
			repository.save(c2);
			repository.save(c3);
			repository.save(new CardPile("1", "1 pik"));
			repository.save(new CardPile("5", "1 bez atu"));

			repository.save(new CardPile("1", "foundation"));
			repository.save(new CardPile("2", "foundation"));
			repository.save(new CardPile("3", "foundation"));


		//	cardRepository.save(new AddingCards(new CardPile("1 karo", "negat")));
			cardRepository.save(new AddingCards(c1,Suit.SPADES,Face.DEUCE));
			cardRepository.save(new AddingCards(c1, Suit.CLUBS, Face.ACE));

			cardRepository.save(new AddingTurnedBackCards(c2));
			cardRepository.save(new AddingCards(c2, Suit.SPADES, Face.ACE));

			cardRepository.save(new AddingTurnedBackCards(c3));
			cardRepository.save(new AddingCards(c3, Suit.HEARTS, Face.ACE));

			// fetch all customers
			log.info("Piles found with findAll():");
			log.info("-------------------------------");
			for (CardPile cardPile : repository.findAll()) {
				log.info(cardPile.toString());
			}
            log.info("");

			// fetch all customers
			log.info("Cards found with findAll():");
			log.info("-------------------------------");
			for (Card crd: cardRepository.findAll()) {
				log.info(crd.toString());
			}
			log.info("");

			// fetch all accounts for cardPile
			log.info("Cards found with findByCardPile():");
			log.info("-------------------------------");
			for (Card acc: cardRepository.findByCardPile(c1)) {
				log.info(acc.toString());
			}
			log.info("");


			// fetch an individual cardPile by ID
			CardPile cardPile = repository.findOne(1L);
			log.info("Piles found with findOne(1L):");
			log.info("--------------------------------");
			log.info(cardPile.toString());
            log.info("");

			// fetch customers by last name
			log.info("CardPile found with findByPileTyp('tableaux'):");
			log.info("--------------------------------------------");
			for (CardPile tabPile : repository.findByPileTyp("tableaux")) {
				log.info(tabPile.toString());
			}
            log.info("");
			// fetch customers by last name
			log.info("CardPile found with findByLastNameStartsWithIgnoreCase('tableaux'):");
			log.info("--------------------------------------------");
			for (CardPile tabPile : repository
					.findByPileTypStartsWithIgnoreCase("tab")) {
				log.info(tabPile.toString());
			}
			log.info("");

		};
	}

}
