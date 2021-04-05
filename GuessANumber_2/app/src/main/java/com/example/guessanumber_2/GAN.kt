package com.example.guessanumber_2

import kotlin.random.Random

class GAN(var maxAttempts : Int, var maxValue: Int) {
    private var toGuess=0
    private var _attempts=0
    val attempts
    get()=_attempts

    enum class Answer{
        TOOBIG, YOULOOSE, YOUWIN, TOOSMALL
    }

    fun getState(): IntArray {
        return intArrayOf(toGuess, _attempts, maxAttempts)
    }

    fun setState(state: IntArray){
        toGuess=state[0]
        _attempts=state[1]
        maxAttempts=state[2]
    }

    fun new(){
        toGuess = Random.nextInt(maxValue)+1
        _attempts=0
    }

    fun check(guess: Int) : Answer{
        _attempts+=1
        if(guess == toGuess)
            return Answer.YOUWIN
        if(_attempts >= maxAttempts)
            return Answer.YOULOOSE
        if(guess > toGuess)
            return Answer.TOOBIG
        return Answer.TOOSMALL
    }

}