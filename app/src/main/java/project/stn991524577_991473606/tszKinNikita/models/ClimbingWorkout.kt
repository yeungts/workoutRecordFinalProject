package project.stn991524577_991473606.tszKinNikita.models

class ClimbingWorkout(id: String, userID: String, date: String, val time: String, val steps: Int): Workout(id, userID, date) {
    override fun getWorkoutDate(): String {
        return date
    }

    override fun getSportName(): String {
        return "Climbing"
    }

    override fun getSummary(): String {
        return "Climbing summary"
    }
}