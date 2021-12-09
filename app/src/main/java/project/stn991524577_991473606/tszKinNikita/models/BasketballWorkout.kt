package project.stn991524577_991473606.tszKinNikita.models

class BasketballWorkout(id: String, userID: String, date: String, val distance: String,
                        val points: Int, val assists: Int, val rebounds: Int): Workout(id, userID, date) {

    override fun getWorkoutDate(): String {
        return date
    }

    override fun getSportName(): String {
        return "Basketball"
    }

    override fun getSummary(): String {
        return "Basketball summary"
    }
}