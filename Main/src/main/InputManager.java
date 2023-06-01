
package main;

import java.util.Scanner;

public class InputManager {
    private Manager manager = new Manager();
    private Scanner scan = new Scanner(System.in);
    public void Start(){
        String input = null;
        System.out.println("Ornek islemler --> ekle,2.1,Hızlı Okuma"
                    + " | sil,2.1,Hızlı Okuma"
                    + " | yazdir"
                    + " | cikis");
        do{
            System.out.println("\n*********************************");
            System.out.print("\nIsleminizi girin: ");
            input = scan.nextLine();
        }while(manager.islemYap(input));
    }
}
