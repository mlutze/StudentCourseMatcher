# Student–Course Matcher

This program is a Java command line application implementing the student–course matching system described in [The Mathematical Sorting Hat](https://www.macalester.edu/~abeverid/papers/sortinghat.pdf).

The program improves on the running time of the original implementation by utilizing the [Jonker-Volgenant Linear Assignment Problem algorithm](https://link.springer.com/article/10.1007/BF02278710).

## Usage

### Input Files

The program takes 4 headered CSV files as input, describing the students and courses to be matched:

#### Qualifications

The Qualifications file contains information on the students to be matched. The first column lists student IDs. All other columns contain boolean values denoting whether or not each student possesses the qualification named by the header.

```csv
student,qualification1,qualification2
alice,true,true
bob,false,true
```

#### Preferences

The Preferences file contains information on the students' preferred courses. The first column lists student IDs. All other columns contain the IDs of the courses preferred by each student, in order of decreasing preference.

```csv
student,preference1,preference2,preference3
alice,course1,course2,course3
bob,course3,course2,course1
```

#### Courses

The Courses file contains information on the seats fillable by students in each course. The first column lists course IDs. All other columns contain non-negative integers denoting the number of seats in the course of the seat type named by the header.

```csv
course,seatType1,seatType2,seatType3
course1,0,5,10
course2,5,5,5
course3,4,3,12
```

#### Seat Types

The Seat Types file contains information on the qualifications for each seat type. The first columns list seat type IDs. The second column is blank or contains `false`, denoting whether the seat may or may not be empty, respectively. All other columns are contain blank or boolean values denoting whether a qualification (named by the header) is required (`true`), forbidden (`false`), or irrelevant (blank) for a student to qualify for a seat type.

```csv
seatType,empty,qualification1,qualification2
seatType1,false,,true
seatType2,,true,true
seatType3,false,false,false
```

### Output

#### Output file

The output is a headered CSV file containing the student–class matches. The first column lists the student IDs and the second column lists the respective course.

```csv
student,course
alice,course1
bob,course2
```

#### Analysis

The program prints to standard output a brief summary of the number of students matched to each preference number, as well as non-preference matches and illegal matches.

### Command-line options

#### Required
* `-p` Preferences file
* `-q` Qualifications file
* `-c` Courses file
* `-s` Seat Types file
* `-o` Output file
* `-a` Alpha value: the number of students to move down from preference `n-1` to `n` in order to move another student from preference `n+1` to `n`

#### Optional
* `-b` Allow blank preferences in Preferences file **(default false)**
* `-u` Allow unknown courses in Preferences file (these will be treated as if blank) **(default false)**
* `-n` Non-preference assignment cost: the number of students to move down from *second-to-last preference* to *last preference* in order to move another student from a *non-preference* to *last preference* **(default equal to alpha value)**
* `-i` Illegal assignment cost: the number of students to move down from *second-to-last preference* to *last preference* in order to move another student from a *non-qualifying seat* to a *qualifying seat* **(default equal to alpha value)**

#### Help
* `-h` Display brief usage information
