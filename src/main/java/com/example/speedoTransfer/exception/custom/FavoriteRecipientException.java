package com.example.speedoTransfer.exception.custom;

public class FavoriteRecipientException extends Throwable {
    public FavoriteRecipientException(String userHasNotFavoriteRecipients) {
        super(userHasNotFavoriteRecipients);
    }
}
