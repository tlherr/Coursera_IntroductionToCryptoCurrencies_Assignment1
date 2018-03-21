package com.tlherr.Coursera.Assignment_1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;

class TxHandlerTest {

    /**
     * true if:
     * (1) all outputs claimed by {@code tx} are in the current com.tlherr.Coursera.Assignment_1.UTXO pool,
     * (2) the signatures on each input of {@code tx} are valid,
     * (3) no com.tlherr.Coursera.Assignment_1.UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    @Test
    void isValidTx() {

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(1024);


        KeyPair user1KeyPair = keyPairGenerator.genKeyPair();
        KeyPair user2KeyPair = keyPairGenerator.genKeyPair();


        //Make a Pool
        UTXOPool currentPool = new UTXOPool();

        Transaction firstTransaction = new Transaction();

        //Give Person 1 3 coins
        firstTransaction.addOutput(3.0, user1KeyPair.getPublic());

        //Finalize the transaction
        firstTransaction.finalize();

        //Make a new UTXO from the transaction
        UTXO firstUTXO = new UTXO(firstTransaction.getHash(), 0);

        //Add it to the pool
        currentPool.addUTXO(firstUTXO, firstTransaction.getOutput(0));

        TxHandler handler = new TxHandler(currentPool);

        assertEquals(true,handler.isValidTx(firstTransaction));

    }

    @Test
    void handleTxs() {
    }
}