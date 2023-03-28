/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skripsi_ihtiaralfath;

import java.math.BigInteger;

/**
 *
 * @author User
 */
public class processRSA {
    public String enkripsi;
    public BigInteger [] cipher;
    
    public processRSA(){
        this.enkripsi = "";
        this.cipher = new BigInteger[]{};
    }
    
    public processRSA(String newEnkripsi, BigInteger []newcipher){
        this.enkripsi = newEnkripsi;
        this.cipher = newcipher;
    }
    
    public void setEnkripsi(String encrypt){
        this.enkripsi = encrypt;
    }
    
    public String getEnkripsi(){
        return this.enkripsi;
    }
    
    public void setCipher(BigInteger [] cipher){
        this.cipher = cipher;
    }
    
    public BigInteger [] getCipher(){
        return this.cipher;
    }
}
