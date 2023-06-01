
package main;

import java.util.HashMap;

public class AddTitle {
    public AddTitle(){
        
    }
    //ekleme işlemini araya yapar.
    private void araya_ekle(BglListe.Node node,BglListe.Node new_node){
        new_node.Next = node.Next;
        node.Next = new_node;
    }
    //eğer kullanıcının eklemek istediği yer 1 ise bu head e denk gelir 
    //bu sebepten ekleme işlemi heade yapılır eleman olsada olmasada
    private void head_ekle(BglListe liste,BglListe.Node new_node){
        new_node.Next = liste.Head;
        liste.Head = new_node;
    }
    //İlgili duzeyde aynı ,isimli bir başlık mevcutmu onu kontrol eder
    private boolean baslik_kontrol(BglListe liste,String title){
        BglListe.Node node = liste.Head;
        while(node!=null){
            if(node.getTitle().equals(title)){
                return true;
            }
            node = node.Next;
        }
        return false;
    }
    //kullanıcının girdigi girdiyi parse edip ilgili değinkenler atadık
    //kullanım kolaylığından dolayi.
    private HashMap<String,String> parseLine(String input){
        HashMap<String,String> command = new HashMap<>();
        String[] tempInput = input.split(",");
        command.put("command",tempInput[0]);
        if(!(tempInput[0].equals("yazdir"))){
            command.put("way",tempInput[1]);
            command.put("title",tempInput[2]);
        }
        return command;
    }
    
    public boolean KomutKontrol(String line,BglListe list){
        try{
            HashMap<String,String> tempHash = parseLine(line);
            if(tempHash.get("command").equals("ekle")){
            addTitle(list,tempHash.get("title"),
                    tempHash.get("way"));
            }
            else if(tempHash.get("command").equals("yazdir")){
                return true;
            }
            else if(tempHash.get("command").equals("sil")){
                Delete del = new Delete(tempHash.get("title"),tempHash.get("way"));
                del.SilmeyeBasla(list);
                if(del.silme_kosul){
                    System.out.println("Silindi");return true;
                }
                else if(list.Head!=null)
                    System.out.println("Listede oyle bir eleman yok!!!");
                return false;
            }
            else
                System.out.println("Hatali komut ile işlem yapmaya çalıştınız");
        }
        catch(Exception e){
            System.out.println("Hatali satir girdisi yaptiniz!!!");
            System.out.println("komut,yol,baslik_adı seklinde girdi yapınız");
        }
        return false;
    }
    //ekleme işlemi 2.1.1.1 yapılmak istenirse ve 2.1.1 elemanı yoksa 
    //2.1.1 dizisini dondurur
    private String Way(String[] way,int flag){
        String x = "";
        int i = 0;
        while(i!=flag+1){
            x+=way[i];//
            if(i!=flag)x+=".";
            i++;
        }
        return x;
    }
    //gerekli koşullar sağlandığında ekleme işlemi yapar
    private void addTitle(BglListe list,String title,String yol){
        //gelen komutu parse edip donen değeri tuttuk
        //kullanıcının eklemek istedigi yolu : dan split edip 
        //String dizisine attık
        String[] way = yol.split("\\.");
        //gecici list tutan yani liste basını tutan değişken ve node tutan değişken tanımlamaları
        BglListe tempList = list;
        BglListe.Node travelNode = tempList.Head;
        //ekleme işleminin yapılacaği yeni dugum oluşturma
        //eleman ekleme işlemi olsa da olmasa da oluşum olacaktır
        BglListe.Node new_node = new BglListe.Node(title);
        //dongude kac dugum oldugnu tutan değişken
        int flag = 1;
        //ekleme işlemi hakkında bilgi veren ekran cıktısı için gerekli uyarıları tutan değişken
        String outputScreen = null;
        //iç dongu kırılınca dış donguyu kırmayı sağlamak için boolean değişken
        boolean cond = true;
        //Girilen komuttaki nesil sayısı kadar doner
        for(String parseWay : way){
            //eklenmek istenen yolu string veri tipinden integer veri tipine donuşturur
            int turn = Integer.parseInt(parseWay);
            //Eger head yoksa yani null ise eklenmesi için kosul calısır
            if(tempList.Head == null){
                //Sonraki nesil yoksa ekleme işlemini heade yapar
                if(flag==way.length){
                    //ekleme işlemi
                    tempList.Head = new_node;
                    //kullanıcı basa eklemek isterse farklı bastan farklı bir yere eklemek isterse farklı uyarılar vericektir
                    outputScreen = turn==1?"Basa ekleme yapıldı":
                            "ilgili seviyede daha öncede eklenmiş bir"
                            +" başlık bulunmadığı için ilk sıraya eklenmiştir";
                    break;
                }
                else{
                    //head bossa ve en az bir nesil daha varsa ekleme işlemi yapılmaz
                    outputScreen = "Girilen Yolda Ekleme Yapılamaz "
                            +"Cunku baş oluşumu yok";
                    break;
                }
            }
            //head varsa ilgili işlemler
            else {
                //eğer head bos değilse ve baska nesil yoksa ve ekleme işlemi heade yapılmak istenirse
                if(tempList.Head!=null&&flag==way.length&&turn==1){
                    //ilgili duzeyde aynı başlıktan elemean varsa 
                    if(baslik_kontrol(tempList, new_node.getTitle())){
                        //eğer varsa ekran uyarısı verilir ve bitiş
                        outputScreen = "ilgili seviyede "+new_node.getTitle()+" adında bir başlık mevcuttur";
                        break;
                    }
                    //ekleme işlemi heade yapılır
                    head_ekle(tempList, new_node);
                    //ve mesaj verilir
                    outputScreen = "Basta baslık oldugu için yeni eleman head oldu";
                    break;
                }
                //O anki nesilde hangi yan başlıkta eklenmek isteniyorsa oraya kadar next yapılır
                while(turn>1){
                    //next null oldugunda ekleme işlemi için kontol yapılır
                    if(travelNode.Next==null){
                        //next null ise ve başka nesil yoksa 
                        if(flag==way.length){
                            //aynı zamanda ilgili duzeyde aynı baslık mevcutsa ekleme işlemi yapmaz ve uyarı verir
                            if(baslik_kontrol(tempList, new_node.getTitle())){
                                outputScreen = "ilgili seviyede "+new_node.getTitle()
                                        +" adında bir başlık mevcuttur";
                                cond=false;
                                break;
                            }
                            //eğer aynı başlıktan yoksa ekleme yapar ve gereken mesajı ekrana bastırır
                            outputScreen = turn>2?"ilgili seviyede istenilen "
                                    +"sayıda başlık bulunmadığı için en son sıraya eklenmiştir":
                                    "Ekleme işlemi yanına yapıldı";
                            travelNode.Next = new_node;
                        }
                        //next null ise ve baska nesil varsa ekleme işlemi yapılmaz 
                        else{
                            String x = (flag==way.length)?way[flag-2]:way[flag-1];
                            outputScreen = "Hiyerarşide "+x+" başlığı bulunmadığından ekleme yapılamamaktadır";
                        }
                        //dış dongu bitirmek için
                        cond = false;
                        break; 
                    }
                    //next null değilse 
                    else{
                        //eklenmek istenen baslığın yeri next ise ve başka nesil yoksa
                        if(turn==2&&flag==way.length){
                            //ve ilgili duzeyde aynı isimde basşlık yoksa mesaj verir ve ekleme yapmaz
                            if(baslik_kontrol(tempList, new_node.getTitle())){
                                outputScreen = "Ilgili seviyede "+new_node.getTitle()+" adında bir başlık mevcuttur";
                                cond=false;
                                break;
                            }
                            //ama aynı baslık yoksa araya ekleme işlemi yapar
                            araya_ekle(travelNode, new_node);
                            outputScreen = "Araya Ekleme Yapıldı";
                            cond = false;
                            break;
                        }
                    }
                    travelNode = travelNode.Next;
                    turn--;
                }
                //eger icerde bir yerde ekleme işlemi yapıldıysa donguyu bitirir kırar.
                if(!cond)break;
                //istenen elemanın altnexti varsa altnexte geçiş yapar ve headi tutar  
                if(travelNode.altNext != null){
                    tempList = travelNode.altNext;
                    travelNode = tempList.Head;
                }
                //ama altnext yoksa ?
                else{
                    //altnext yoksa ve sadece o olmayan altnext e eklenmek isteniyorsa oluşturur
                    if(flag==way.length-1){
                        travelNode.altNext = new BglListe();
                        tempList = travelNode.altNext;
                        travelNode = tempList.Head;
                    }
                    //altnext yoksa ve olmayan altnextin altnextine eklenmek istenirse ekleme yapılmaz ve sebebini uyari verur
                    else{
                        outputScreen = "Hiyerarşide "+Way(way,flag)
                                +" başlığı bulunmadığından ekleme yapılamamaktadır";
                        break;
                    }
                }
            }
            //her nesil atladıgında flag artar
            flag++;
        }
        //bütün işlemler yapıldıktan sonra gereken uyarı neyse ekrana bastırır.
        System.out.println(outputScreen);
    }
}
