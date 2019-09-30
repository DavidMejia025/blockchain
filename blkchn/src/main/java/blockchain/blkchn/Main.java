package blockchain.blkchn;

import core.Block;
import core.BlockChainImpl;
import core.Client;
import core.Transaction;
import core.WorkerImpl;

/**
 * Hello world!
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
      //String hash = Hashes.calculateHash("hola");
      WorkerImpl w1 = new WorkerImpl("192.87.456.10");
      
      Client matt = new Client("192.54.345.17", "Matt");
      
      w1.createBlockChain();
      
      BlockChainImpl validBlockChain = w1.getBlockChain();
      
      System.out.println( validBlockChain.getLastBlock());
      
      w1.mineBlock();
      
      System.out.println( validBlockChain.getLastBlock());
      
      WorkerImpl w2 = new WorkerImpl("192.37.456.10");
      
      w1.addWorker(w2);
      
      Transaction newTransaction = matt.createTransaction("recieverPublicKey", 100);
      
      w2.addTransaction(newTransaction);
      
      w2.mineBlock();
      //Client tom = new Client("192.53.345.17", "Tom");
      
      System.out.println( w1.getBlockChain().getIndex());
      
      System.out.println( w2.getBlockChain().getIndex());
      
      Block lastBlock = w2.getBlockChain().getLastBlock();
      System.out.println(lastBlock.getPrevHash() );
      
      System.out.println( "Hello World!" );
    }
}
/*test protocol
1. create a worker
2. create a transaction -> create a client that creates a transaction
3. create a block chain
   create the root block
4. mine a new block 
5. create a new worker
6. attach the bc
7. create transactions *Reward the miner (us) by adding a transaction granting us 1 coin
8  w2 mine a block
9. notify w1
10. if newB is valid
    w1 attach the newB.
    

*/