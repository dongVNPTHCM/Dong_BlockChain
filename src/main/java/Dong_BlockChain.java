import java.util.ArrayList;
import com.google.gson.GsonBuilder;
import java.util.Scanner;
public class Dong_BlockChain {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;
    public static void main(String[] args) {
//add our blocks to the blockchain ArrayList:
        Scanner BlockData = new Scanner(System.in);

        System.out.println("Hai nhap so luong khoi cua chuoi: ");
        int i = Integer.parseInt(BlockData.nextLine());
        //int i = BlockData.nextInt();
        if (i > 0)
        {
            for (int j = 0; j < i; j++) {
                if (j == 0) {
                    System.out.println("Khoi tao thong tin khoi dau tien!");
                    String data = BlockData.nextLine();
                    blockchain.add(new Block(data, "0"));
                    System.out.println("Trying to Mine block 0... ");
                    blockchain.get(j).mineBlock(difficulty);
                } else {
                    System.out.println("Nhap du lieu khoi thu: " + j);
                    System.out.println("Nhap ho va ten: ");
                    String hoten = BlockData.nextLine();
                    System.out.println("Nhap ma hop dong: ");
                    String maHD = BlockData.nextLine();
                    //String chuoi= hoten.concat(maHD);
                    String chuoi = (new StringBuilder()).append(hoten).append("-").append(maHD).toString();
                    blockchain.add(new Block(chuoi, blockchain.get(blockchain.size() - 1).hash));
                    System.out.println("Trying to Mine block " + j);
                    blockchain.get(j).mineBlock(difficulty);
                }
            }
            System.out.println("\nBlockchain is Valid: " + isChainValid());
            String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
            System.out.println("\nThe block chain: ");
            System.out.println(blockchainJson);
        }
        else
        {
            System.out.printf("SO KHOI PHAI LON HON 0");
        }

    }


    //Phương thức kiểm tra tính toàn vẹn của Blockchain
    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');
//loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
//compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                System.out.println("Current Hashes not equal");
                return false;
            }
//compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("Previous Hashes not equal");
                return false;
            }
//check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }
}
