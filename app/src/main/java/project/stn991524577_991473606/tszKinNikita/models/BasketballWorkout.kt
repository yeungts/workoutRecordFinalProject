package project.stn991524577_991473606.tszKinNikita.models

class BasketballWorkout(id: String, userID: String, date: String, val distance: String,
                        val points: Int, val assists: Int, val rebounds: Int): Workout(id, userID, date) {
}