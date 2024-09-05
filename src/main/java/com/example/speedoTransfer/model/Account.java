package com.example.speedoTransfer.model;

import com.example.speedoTransfer.dto.AccountDTO;
import com.example.speedoTransfer.enumeration.AccountCurrency;
import com.example.speedoTransfer.enumeration.AccountType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
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
    private AccountType accountType;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private AccountCurrency currency;

    private String accountName;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "senderAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> sentTransactions = new HashSet<>();

    @OneToMany(mappedBy = "receiverAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Transaction> receivedTransactions = new HashSet<>();

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<FavoriteRecipient> favoriteRecipients = new HashSet<>();


    public AccountDTO toDTO() {
        return AccountDTO.builder()
                .id(this.id)
                .accountNumber(this.accountNumber)
                .accountType(this.accountType)
                .balance(this.balance)
                .currency(this.currency)
                .accountName(this.accountName)
                .active(this.active)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }


}
