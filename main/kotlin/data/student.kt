package data

data class Student (
    val firstname: String,
    val surname: String,
    var absence:Boolean
)

val studentList =
    arrayListOf(
        Student("Sheldon", "Cooper",true),
        Student("Leonard", "Hofstadter",true),
        Student("Howard", "Wolowitz",true),
        Student("Nurgul","Saduova",true)
    )