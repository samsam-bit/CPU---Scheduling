# CPU Scheduling Simulator

## Overview
This project is a Java-based simulation of CPU scheduling algorithms used by Operating Systems to manage process execution. It supports both a Graphical User Interface (GUI) and a Command Line Interface (CLI) for calculating process metrics.

## Key Metrics Calculated
- **Turnaround Time (TAT)**: Total time from process arrival to completion.
  - `TAT = Finish Time - Arrival Time`
- **Waiting Time (WT)**: Total time a process spends waiting in the ready queue.
  - `WT = TAT - Burst Time`
- **Response Time (RT)**: Time from process arrival to first time it gets the CPU.
  - `RT = First Start Time - Arrival Time`

## Implementation Details

### 1. Preemptive Shortest Job First (SJF)
The SJF algorithm implemented here is **preemptive**. The scheduler evaluates the remaining time of all available processes at every single clock tick. The process with the smallest remaining time is granted the CPU.

### 2. Non-Preemptive Priority Scheduling
The Priority algorithm assigns the CPU to the process with the lowest priority value (where lower = higher priority). It is **non-preemptive**, meaning once a process is selected, it will occupy the CPU until its entire burst time is exhausted.

## Usage
### GUI Mode
1. Run `GUI.java`.
2. Enter the number of processes.
3. Fill in the `Arrival`, `Burst`, and `Priority` columns in the table.
4. Click **Run SJF** or **Run Priority** to see the execution order and resulting metrics.

### CLI Mode
1. Run `Main.java`.
2. The program will execute a hardcoded set of processes using the SJF algorithm and print the results to the console.

## Project Structure
- `src/process.java`: Process entity definition.
- `src/scheduler.java`: Algorithm implementations.
- `src/GUI.java`: Swing UI implementation.
- `src/Main.java`: Test entry point.

## Test Cases

### 1. Basic Preemptive SJF
**Input:**
| Process | Arrival | Burst | Priority |
|---------|---------|-------|----------|
| P1 | 0 | 7 | 1 |
| P2 | 2 | 4 | 2 |
| P3 | 4 | 1 | 3 |
| P4 | 5 | 4 | 4 |

**Expected Execution Order:** P1 → P2 → P3 → P2 → P4 → P1
**Expected Gantt Chart:** P1(0-2) | P2(2-4) | P3(4-5) | P2(5-7) | P4(7-11) | P1(11-16)
**Expected Metrics:**
| Process | WT | TAT | RT |
|---------|----|-----|-----|
| P1 | 9 | 16 | 0 |
| P2 | 1 | 5 | 0 |
| P3 | 0 | 1 | 0 |
| P4 | 2 | 6 | 2 |

---

### 2. All Processes Arrive at Same Time
**Input:**
| Process | Arrival | Burst | Priority |
|---------|---------|-------|----------|
| P1 | 0 | 5 | 3 |
| P2 | 0 | 3 | 1 |
| P3 | 0 | 8 | 4 |
| P4 | 0 | 2 | 2 |

**Expected Execution Order:** P4 → P2 → P1 → P3
**Expected Metrics:** P4 shortest burst (2) executes first, followed by P2 (3), P1 (5), P3 (8)

---

### 3. Processes Arrive in Reverse Order (Longer Arrivals First)
**Input:**
| Process | Arrival | Burst | Priority |
|---------|---------|-------|----------|
| P1 | 3 | 5 | 1 |
| P2 | 2 | 3 | 2 |
| P3 | 1 | 1 | 3 |
| P4 | 0 | 4 | 4 |

**Expected Execution Order:** P4 → P3 → P2 → P1
**Explanation:** Even though P4 arrives last at time 0, P3 (burst=1) runs first, then P2 (burst=3), then P1 (burst=5)

---

### 4. Idle Time / CPU Idle Period
**Input:**
| Process | Arrival | Burst | Priority |
|---------|---------|-------|----------|
| P1 | 0 | 3 | 1 |
| P2 | 10 | 2 | 2 |

**Expected Behavior:** CPU idle from time 3-10 before P2 arrives.
**Expected Gantt Chart:** P1(0-3) | IDLE(3-10) | P2(10-12)

---

### 5. Single Process Only
**Input:**
| Process | Arrival | Burst | Priority |
|---------|---------|-------|----------|
| P1 | 0 | 10 | 1 |

**Expected Execution Order:** P1 only
**Expected Metrics:** WT=0, TAT=10, RT=0

---

### 6. Same Burst Time (Tie-Breaking)
**Input:**
| Process | Arrival | Burst | Priority |
|---------|---------|-------|----------|
| P1 | 0 | 5 | 1 |
| P2 | 0 | 5 | 2 |
| P3 | 0 | 5 | 3 |

**Expected Behavior:** When bursts are equal, first-arrived process gets priority (FCFS tie-breaking)
**Expected Execution Order:** P1 → P2 → P3

---

### 7. Late Arrivals (All Processes Arrive Late)
**Input:**
| Process | Arrival | Burst | Priority |
|---------|---------|-------|----------|
| P1 | 5 | 2 | 1 |
| P2 | 8 | 4 | 2 |
| P3 | 3 | 1 | 3 |

**Expected Execution Order:** IDLE(0-3) → P3 → P1 → P2
**Expected Gantt Chart:** IDLE(0-3) | P3(3-4) | P1(4-6) | P2(6-10)
