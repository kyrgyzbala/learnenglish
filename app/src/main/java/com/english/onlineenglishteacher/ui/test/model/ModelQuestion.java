package com.english.onlineenglishteacher.ui.test.model;

public class ModelQuestion {

    private String question;
    private String varA;
    private String varB;
    private String varC;
    private String varD;
    private int answer;

    public ModelQuestion() {
    }

    public ModelQuestion(String question, String varA, String varB, String varC, String varD, int answer) {
        this.question = question;
        this.varA = varA;
        this.varB = varB;
        this.varC = varC;
        this.varD = varD;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getVarA() {
        return varA;
    }

    public void setVarA(String varA) {
        this.varA = varA;
    }

    public String getVarB() {
        return varB;
    }

    public void setVarB(String varB) {
        this.varB = varB;
    }

    public String getVarC() {
        return varC;
    }

    public void setVarC(String varC) {
        this.varC = varC;
    }

    public String getVarD() {
        return varD;
    }

    public void setVarD(String varD) {
        this.varD = varD;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
