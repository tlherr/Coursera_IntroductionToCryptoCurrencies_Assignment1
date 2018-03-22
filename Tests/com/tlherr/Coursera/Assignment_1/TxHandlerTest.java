package com.tlherr.Coursera.Assignment_1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.*;

import static org.junit.jupiter.api.Assertions.*;

class TxHandlerTest {

    @Test
    void singleTransactionTest() {

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(1024);


        KeyPair user1KeyPair = keyPairGenerator.genKeyPair();


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

        assertEquals(false, handler.isValidTx(firstTransaction));
    }

    @Test
    void unsignedInputTransactionTest() {

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyPairGenerator.initialize(1024);


        KeyPair user1KeyPair = keyPairGenerator.genKeyPair();


        //Make a Pool
        UTXOPool currentPool = new UTXOPool();

        Transaction firstTransaction = new Transaction();

        //Give Person 1 3 coins
        firstTransaction.addOutput(3.0, user1KeyPair.getPublic());

        firstTransaction.finalize();

        //Add it to the pool
        currentPool.addUTXO(new UTXO(firstTransaction.getHash(), 0), firstTransaction.getOutput(0));


        //Make a second transaction and attempt to double spend
        Transaction secondTransaction = new Transaction();

        //Add input
        secondTransaction.addInput(firstTransaction.getHash(), 0);

        //Do not sign transaction, should cause it to be invalid

        secondTransaction.addOutput(3.0, user1KeyPair.getPublic());

        secondTransaction.finalize();

        currentPool.addUTXO(new UTXO(secondTransaction.getHash(), 0), secondTransaction.getOutput(0));
        currentPool.addUTXO(new UTXO(secondTransaction.getHash(), 0), secondTransaction.getOutput(1));

        TxHandler handler = new TxHandler(currentPool);

        assertEquals(false, handler.isValidTx(firstTransaction));
        assertEquals(false, handler.isValidTx(secondTransaction));
    }

    @Test
    void validTransactionTest() {

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
        firstTransaction.addOutput(3, user1KeyPair.getPublic());

        firstTransaction.finalize();

        //Add it to the pool
        currentPool.addUTXO(new UTXO(firstTransaction.getHash(), 0), firstTransaction.getOutput(0));


        Transaction secondTransaction = new Transaction();

        //Add an output, user 1 is paying user 2
        secondTransaction.addOutput(3, user2KeyPair.getPublic());

        //Add input, first transaction
        secondTransaction.addInput(firstTransaction.getHash(), 0);

        //Input must be signed
        try {
            Signature inputSig = Signature.getInstance("SHA256withRSA");

            //Signing requires usage of private key
            inputSig.initSign(user1KeyPair.getPrivate());

            //Supply the data to be signed
            inputSig.update(secondTransaction.getRawDataToSign(0));

            //Generate the signature
            byte[] signature = inputSig.sign();

            secondTransaction.addSignature(signature, 0);

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }


        secondTransaction.finalize();

        currentPool.addUTXO(new UTXO(secondTransaction.getHash(), 0), secondTransaction.getOutput(0));

        TxHandler handler = new TxHandler(currentPool);

        assertEquals(false, handler.isValidTx(firstTransaction));
        assertEquals(true, handler.isValidTx(secondTransaction));
    }

    @Test
    void doubleSpendTransactionTest() {

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

        firstTransaction.finalize();

        //Add it to the pool
        currentPool.addUTXO(new UTXO(firstTransaction.getHash(), 0), firstTransaction.getOutput(0));


        //Make a second transaction and attempt to double spend
        Transaction secondTransaction = new Transaction();

        //Add input
        secondTransaction.addInput(firstTransaction.getHash(), 0);

        //Input must be signed
        try {
            Signature inputSig = Signature.getInstance("SHA256withRSA");

            //Signing requires usage of private key
            inputSig.initSign(user1KeyPair.getPrivate());

            //Supply the data to be signed
            inputSig.update(secondTransaction.getRawDataToSign(0));

            //Generate the signature
            byte[] signature = inputSig.sign();

            secondTransaction.addSignature(signature, 0);

        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }


        secondTransaction.addOutput(3.0, user1KeyPair.getPublic());
        secondTransaction.addOutput(3.0, user2KeyPair.getPublic());

        secondTransaction.finalize();

        currentPool.addUTXO(new UTXO(secondTransaction.getHash(), 0), secondTransaction.getOutput(0));
        currentPool.addUTXO(new UTXO(secondTransaction.getHash(), 0), secondTransaction.getOutput(1));

        TxHandler handler = new TxHandler(currentPool);

        assertEquals(false, handler.isValidTx(firstTransaction));
        assertEquals(false, handler.isValidTx(secondTransaction));
    }

}