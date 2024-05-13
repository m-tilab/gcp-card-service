package org.example.gcpcardservice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class GcpCardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GcpCardServiceApplication.class, args);
    }

}

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
class CardController {

    private final CardService cardService;

    @PostMapping
    IssueCardResponse issueNewCard(@RequestBody IssueCardRequest issueCardRequest) {

        final Card card = new Card(issueCardRequest.card(), issueCardRequest.description());
        final Card savedCard = cardService.issueNewCard(card);

        return new IssueCardResponse(savedCard.getId(), savedCard.getCard(), savedCard.getDescription());
    }

    @GetMapping
    Long countCards() {

        return cardService.countCard();
    }
}

record IssueCardRequest(String card, String description) {
}

record IssueCardResponse(Long id, String card, String description) {
}

@RequiredArgsConstructor
@Service
class CardService {

    private final CardRepository repository;

    Card issueNewCard(Card card) {
        return repository.save(card);
    }

    Long countCard() {
        return repository.count();
    }


}

@Repository
interface CardRepository extends JpaRepository<Card, Long> {
}


@Entity
@Getter
@NoArgsConstructor
class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String card;
    private String description;

    public Card(String card, String description) {
        this.card = card;
        this.description = description;
    }
}
