# CPU Scheduling Simulator

## Algorithms
- SJF (Shortest Job First)

## Features
- GUI using Java Swing
- Input table
- Execution order
- Waiting Time & Turnaround Time

## How to Run
1. Run GUI.java
2. Enter number of processes
3. Fill table
4. Click Run

## Example Scenario

### Input

| Process | Arrival Time | Burst Time | Priority |
|--------|--------------|------------|----------|
| P1     | 0            | 6          | 2        |
| P2     | 1            | 3          | 1        |
| P3     | 2            | 8          | 3        |
| P4     | 3            | 2          | 2        |

### Scheduling Algorithm
Shortest Job First (SJF - Non Preemptive)

### Execution Order
P1 → P4 → P2 → P3

### Output

| Process | WT | TAT |
|--------|----|-----|
| P1     | 0  | 6   |
| P4     | 3  | 5   |
| P2     | 7  | 10  |
| P3     | 9  | 17  |

## Scenario 2

### Input

| Process | Arrival Time | Burst Time | Priority |
|--------|--------------|------------|----------|
| P1     | 0            | 4          | 2        |
| P2     | 1            | 5          | 1        |
| P3     | 2            | 2          | 3        |
| P4     | 3            | 1          | 2        |

### Scheduling Algorithm
Shortest Job First (SJF - Non Preemptive)

### Execution Order
P1 → P4 → P3 → P2

### Output

| Process | WT | TAT |
|--------|----|-----|
| P1     | 0  | 4   |
| P4     | 1  | 2   |
| P3     | 3  | 5   |
| P2     | 6  | 11  |

### Notes
- Priority values are included but not used in SJF.
- The scheduler always selects the shortest burst time among arrived processes.
