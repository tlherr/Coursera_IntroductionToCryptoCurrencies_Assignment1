package com.tlherr.Coursera.Assignment_1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TxHandlerTest {

    @BeforeEach
    void setUp() {



    }

    @AfterEach
    void tearDown() {
    }

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

    }

    @Test
    void handleTxs() {
    }
}