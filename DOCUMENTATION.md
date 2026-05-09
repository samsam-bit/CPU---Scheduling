# CPU Scheduling Simulator

## Overview
This project is a Java-based simulation of CPU scheduling algorithms used by Operating Systems to manage process execution. It supports both a Graphical User Interface (GUI) and a Command Line Interface (CLI) for calculating process metrics.

## Key Metrics Calculated
- **Turnaround Time (TAT)**: Total time from process arrival to completion.
  - `TAT = Finish Time - Arrival Time`
- **Waiting Time (WT)**: Total time a process spends waiting in the ready queue.
  - `WT = TAT - Burst Time`

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
