package ca.bcit.comp2522.gameproject.numbergame;

interface NumberGameInterface
{
    void startNewGame();
    void placeNumber(int position);
    boolean isGameOver();
    void showScore();
    int getNextNumber();
    boolean isValidPlacement(int position);
}