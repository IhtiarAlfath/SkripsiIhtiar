package skripsi_ihtiaralfath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Key_RSA {
    public BigInteger p, q, N, toten, d, e;
    public final static int bits = 2048;
    SecureRandom  r = new SecureRandom ();
    
    public Key_RSA(){
        this.p = BigInteger.ZERO;
        this.q = BigInteger.ZERO;
        this.N = BigInteger.ZERO;
        this.toten = BigInteger.ZERO;
        this.d = BigInteger.ZERO;
        this.e = BigInteger.ZERO;
    }
    
    public void setP(BigInteger p){
        this.p = p;
    }
    
    public BigInteger getP(){
        return this.p;
    }
    
    public void setQ(BigInteger q){
        this.q = q;
    }
    
    public BigInteger getQ(){
        return this.q;
    }

    public void setN(BigInteger N){
        this.N = N;
    }
    
    public BigInteger getN(){
        return this.N;
    }

    public void setToten(BigInteger phiN){
        this.toten = phiN;
    }
    
    public BigInteger getToten(){
        return this.toten;
    }

    public void setD(BigInteger d){
        this.d = d;
    }
    
    public BigInteger getd(){
        return this.d;
    }   
    
    public void setE(BigInteger e){
        this.e = e;
    }
    
    public BigInteger getE(){
        return this.e;
    }   

    public static BigInteger generateProbablePrime(int bitSize){
        return BigInteger.probablePrime(bitSize, new SecureRandom ());
    }

    public static BigInteger calculateN(BigInteger p, BigInteger q){
        return p.multiply(q);
    }

    public static BigInteger toten(BigInteger p, BigInteger q){
        BigInteger toten = p.subtract(BigInteger.ONE);
        toten = toten.multiply(q.subtract(BigInteger.ONE));
        return toten;
    }

    public static BigInteger gcd(BigInteger e, BigInteger n){
        if(n.equals(BigInteger.ZERO))
            return e;
        return gcd(n, e.mod(n));
    }

    
    public BigInteger generateE(BigInteger toten){
         BigInteger elocal = new BigInteger(bits, 100, r);
         BigInteger gcd = gcd(elocal, toten);
         while (elocal.compareTo(toten) == 1 || elocal.compareTo(BigInteger.ONE)
                 == -1 || gcd.equals(BigInteger.ONE) == false){
             generateE(toten);
         }
         return elocal;
    }

    public Key_RSA generateKey(){
        Key_RSA key = new Key_RSA();
        key.setP(generateProbablePrime(bits));
        key.setQ(generateProbablePrime(bits));
        key.setN(calculateN(key.p,key.q));
        key.setToten(toten(key.p,key.q));
        key.setE(generateE(key.toten));          
        //generate the d decryption exponent
        key.setD(key.e.modInverse(key.toten)); 
        return key;
    }    
    
    public static int saveFileKeyRSA(Key_RSA key, String fName){
        try{
            File myPublicKey = new File(fName + "-pbc" + ".rsapublickey");
            if(myPublicKey.createNewFile()){
                try (FileWriter myWriter = new FileWriter(fName + "-pbc" + ".rsapublickey")) {
                    String content = key.N.toString() + "," + key.e.toString();
                    myWriter.write(content);
                } catch(Exception exc){
                    System.out.println(exc);
                    return -1;
                }
                System.out.println("Public key saved");          
            } else {
                System.out.println("File already exists.");
                return -1;
            }

            File myPrivateKey = new File(fName + "-pvt" + ".rsaprivatekey");
            if(myPrivateKey.createNewFile()){
                try (FileWriter myWriter = new FileWriter(fName + "-pvt" + ".rsaprivatekey")) {
                    String content = key.N.toString() + "," + key.d.toString();
                    myWriter.write(content);
                }catch(Exception exc){
                    System.out.println(exc);
                    return -1;
                }
                System.out.println("Private key saved");
            } else {
                System.out.println("File already exists.");
                return -1;
            }
            
        }catch(Exception exc){
            System.out.println(exc);
            return -1;
        }
        return 1;
    }
    
    public String[] readKeyRSA(String path){
        String data = "";
        String key[] = {};
        try{
            File myObj = new File(path);
            Scanner readFile = new Scanner(myObj);
            while (readFile.hasNextLine()) {
                data = readFile.nextLine();
                System.out.println(data);
            }
            key = data.split(",");
            readFile.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        return key;
    }
    
    public void setPublicKey(BigInteger N, BigInteger e){
        this.N = N;
        this.e = e;
    }
    
    public void setPrivateKey(BigInteger N, BigInteger D){
        this.N = N;
        this.d = D;
    }
    
    public Key_RSA getPublicKey(){
        Key_RSA key = new Key_RSA();
        key.N = this.N;
        key.e = this.e;
        return key;
    }
    
    public Key_RSA getPrivateKey(){
        Key_RSA key = new Key_RSA();
        key.N = this.N;
        key.d = this.d;
        return key;
    }
    
    public Key_RSA getPublicKey(String [] readKey){
        Key_RSA key = new Key_RSA();
        key.N = new BigInteger(readKey[0]);
        key.e = new BigInteger(readKey[1]);
        return key;
    }
    
    public Key_RSA getPrivateKey(String [] readKey){
        Key_RSA key = new Key_RSA();
        key.N = new BigInteger(readKey[0]);
        key.d = new BigInteger(readKey[1]);
        return key;
    }        
}
