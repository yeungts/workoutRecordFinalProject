package project.stn991524577_991473606.tszKinNikita.models

class RunningWorkout(id: String, date: String, userID: String, val time: String, val distance: String): Workout(id, date, userID) {
    override fun getWorkoutDate(): String {
        return date
    }

    override fun getSportName(): String {
        return "Running"
    }

    override fun getSummary(): String {
        return "Running summary"
    }
}