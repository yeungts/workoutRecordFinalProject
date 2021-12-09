package project.stn991524577_991473606.tszKinNikita.models

class SwimmingWorkout(id: String, userID: String, date: String, val time: String, val distance: String): Workout(id, userID, date) {
    override fun getWorkoutDate(): String {
        return date
    }

    override fun getSportName(): String {
        return "Swimming"
    }

    override fun getSummary(): String {
        return "Swimming summary"
    }
}