
package main;
import main.BglListe.Node;

public class WriteList {
    public WriteList(){
        
    }
    public void TumElemanlarYazdir(BglListe list){
        if(list.Head == null){
            System.out.println("Liste Bos");
            return;
        }
        System.out.println("\n*********************************\n");
        write(list,0,"");
    }
    private void write(BglListe list,int altNextCount,String buffer){
       Node node = list.Head;
       String tempBuffer = buffer;
       int nextCount = 1;
       while(node!=null){
           tempBuffer = altNextCount!=0?buffer+"."+nextCount:nextCount+"";
           System.out.println(tempBuffer+" "+node.getTitle());
           if(node.altNext!=null){
               write(node.altNext,altNextCount+1,"\t"+tempBuffer);
           }
           node = node.Next;
           nextCount++;
       }
    }
    
}
