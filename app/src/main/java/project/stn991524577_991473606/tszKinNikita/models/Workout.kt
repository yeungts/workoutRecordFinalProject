package project.stn991524577_991473606.tszKinNikita.models

abstract open class Workout(val id: String, val userID: String, val date: String) {
    abstract open fun getWorkoutDate(): String

    abstract open fun getSportName(): String

    abstract open fun getSummary(): String
}