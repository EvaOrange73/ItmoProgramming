package lab3;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Story story = new Story();
        //Но чем больше Тигра совал то свой нос, то лапу, то в одну, то в другую банку,
        // тем больше он находил вещей, которые Тигры не любят.
        ArrayList<Eatable> food;
        food = story.tiggerIsLookingForFood();
        // И когда он перерыл весь буфет и нашел все, что там было, и оказалось, что он ничего этого есть не может,
        // он спросил Кенгу, но Кенга, и Кристофер Робин, и Пятачок-- все стояли вокруг Крошки Ру, уговаривая его принять рыбий жир.
        // И Ру говорил: "Может, не надо?"-- а Кенга говорила: "Ну-ну, милый Ру, вспомни, что ты мне обещал".
        if (food.isEmpty()) {
            story.tiggerAskKenga();
        }
    }
}