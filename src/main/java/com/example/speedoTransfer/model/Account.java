package com.example.speedoTransfer.model;

import com.example.speedoTransfer.dto.AccountDTO;
import com.example.speedoTransfer.enumeration.AccountCurrency;
import com.example.speedoTransfer.enumeration.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountCurrency currency;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "senderAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> sentTransactions = new HashSet<>();

    @OneToMany(mappedBy = "receiverAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Transaction> receivedTransactions = new HashSet<>();

//    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    @Builder.Default
//    private Set<FavoriteRecipient> favoriteRecipients = new HashSet<>();


    public AccountDTO toDTO() {
        return AccountDTO.builder()
                .id(this.id)
                .accountNumber(this.accountNumber)
                .balance(this.balance)
                .currency(AccountCurrency.USD)
                .active(this.active)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }


}
