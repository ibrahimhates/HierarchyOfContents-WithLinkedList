
package main;

public class Manager {
    private BglListe liste = new BglListe();
    private AddTitle ekle = new AddTitle();
    private WriteList yazdir = new WriteList();
    
    public boolean islemYap(String input){
        if(input.equals("cikis"))return false;
        else{
            if(ekle.KomutKontrol(input, liste))ListeyiYazdir();
        }
        return true;
    }
    public void ListeyiYazdir(){
        yazdir.TumElemanlarYazdir(liste);
    }
        
}
