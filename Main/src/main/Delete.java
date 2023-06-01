
package main;

public class Delete {
    private String silDugumTitle;
    private String konum;
    private boolean kosul = true;
    public boolean silme_kosul = false;
    
    public Delete(String silDugumTitle,String konum){
        this.silDugumTitle = silDugumTitle;
        this.konum = konum;
    }
    public void SilmeyeBasla(BglListe list){
        if(list.Head == null){
            System.out.println("Liste bos. Silme islemi yapılamaz");
            return;
        }
        deleteTitle(list,0,"");
    }
    private void deleteTitle(BglListe list,int altNextCount,String buffer){
       BglListe.Node node = list.Head;
       BglListe.Node prewNode = node;
       String tempBuffer = buffer;
       int nextCount = 1;
       while(node!=null&&kosul){
           tempBuffer = altNextCount!=0?buffer+"."+nextCount:nextCount+"";
           if(tempBuffer.equals(konum)
                   &&silDugumTitle.equals(node.getTitle())){
               sil(node,prewNode,list);
               kosul = false;
           }
           if(node.altNext!=null&&kosul){
               deleteTitle(node.altNext,altNextCount+1,tempBuffer);
           }
           if(!kosul)continue;
           prewNode = node;
           node = node.Next;
           nextCount++;
       }
    }
    private void sil(BglListe.Node node,BglListe.Node prwNode,BglListe list){
        if(list.Head == node){
            list.Head = prwNode.Next;
            prwNode = null;
        }
        else if(node.Next==null){
            prwNode.Next = null;
        }
        else{
            prwNode.Next = node.Next;
            node.Next = null;
        }
        silme_kosul = true;
    }
}
