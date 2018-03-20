package com.tlherr.Coursera.Assignment_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Class represents current set of outstanding UTXOs and contains a map from each com.tlherr.Coursera.Assignment_1.UTXO to its cooresponding transaction
 * output.
 *
 * Contains constructures to create a new empty com.tlherr.Coursera.Assignment_1.UTXOPool or copy a given pool
 * Also has methods to add/remove UTXOs from the pool and get output of cooresponding of given com.tlherr.Coursera.Assignment_1.UTXO
 * Check if com.tlherr.Coursera.Assignment_1.UTXO is in the pool
 * Get list of all UTXOs in the pool
 */
public class UTXOPool {

    /**
     * The current collection of UTXOs, with each one mapped to its corresponding transaction output
     */
    private HashMap<UTXO, Transaction.Output> H;

    /** Creates a new empty com.tlherr.Coursera.Assignment_1.UTXOPool */
    public UTXOPool() {
        H = new HashMap<UTXO, Transaction.Output>();
    }

    /** Creates a new com.tlherr.Coursera.Assignment_1.UTXOPool that is a copy of {@code uPool} */
    public UTXOPool(UTXOPool uPool) {
        H = new HashMap<UTXO, Transaction.Output>(uPool.H);
    }

    /** Adds a mapping from com.tlherr.Coursera.Assignment_1.UTXO {@code utxo} to transaction output @code{txOut} to the pool */
    public void addUTXO(UTXO utxo, Transaction.Output txOut) {
        H.put(utxo, txOut);
    }

    /** Removes the com.tlherr.Coursera.Assignment_1.UTXO {@code utxo} from the pool */
    public void removeUTXO(UTXO utxo) {
        H.remove(utxo);
    }

    /**
     * @return the transaction output corresponding to com.tlherr.Coursera.Assignment_1.UTXO {@code utxo}, or null if {@code utxo} is
     *         not in the pool.
     */
    public Transaction.Output getTxOutput(UTXO ut) {
        return H.get(ut);
    }

    /** @return true if com.tlherr.Coursera.Assignment_1.UTXO {@code utxo} is in the pool and false otherwise */
    public boolean contains(UTXO utxo) {
        return H.containsKey(utxo);
    }

    /** Returns an {@code ArrayList} of all UTXOs in the pool */
    public ArrayList<UTXO> getAllUTXO() {
        Set<UTXO> setUTXO = H.keySet();
        ArrayList<UTXO> allUTXO = new ArrayList<UTXO>();
        for (UTXO ut : setUTXO) {
            allUTXO.add(ut);
        }
        return allUTXO;
    }
}
