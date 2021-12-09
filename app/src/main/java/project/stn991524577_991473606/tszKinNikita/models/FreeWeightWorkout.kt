package project.stn991524577_991473606.tszKinNikita.models

class FreeWeightWorkout(id: String, userID: String, date: String, val weight: String): Workout(id, userID, date) {
    override fun getWorkoutDate(): String {
        return date
    }

    override fun getSportName(): String {
        return "Free Weight"
    }

    override fun getSummary(): String {
        return "Free Weight summary"
    }
}