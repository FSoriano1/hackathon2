package com.monkeyGame.game;

public class Flashcard {

    private String question;
    private String answer;

    public Flashcard(String q, String a){
        question=q;
        answer=a;
    }

    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
