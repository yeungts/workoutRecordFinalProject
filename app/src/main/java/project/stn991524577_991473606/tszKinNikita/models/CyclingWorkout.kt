package project.stn991524577_991473606.tszKinNikita.models

class CyclingWorkout(id: String, userID: String, date: String, val time: String, val distance: String): Workout(id, userID, date) {
    override fun getWorkoutDate(): String {
        return date
    }

    override fun getSportName(): String {
        return "Cycling"
    }

    override fun getSummary(): String {
        return "Cycling summary"
    }
}