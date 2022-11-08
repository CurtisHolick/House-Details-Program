# House-Details-Program
Program to assign cleaning jobs to people with a random fair system.

I wrote this to help my fraternity distribute cleaning tasks (known as details in this project) across members, with a ranking system based on age and task difficulty.

## Features:
- Create, edit, and save multiple lists of jobs 
- Select people and jobs from a list to ignore on the fly
- Output a list of job assignments, with a variable number of people assigned to each job

### Ranking System
The ranking system is a random bag, where those with a lower ranking are added multiple times. Jobs are then selected by randomly picking names from the bag, starting with the least desirable jobs. Once a name is pulled, all other entries of that name are removed from the bag.

## Technical Details
This application is written entirely in java, and uses the swing package to create the GUI. The save files are just .csv files.

I had originally written it in python using the Tkinter package, but decided to remake it in java as it grew, partialy as a way to practice using swing.

## Walkthrough of Application
### Select existing of cleaning tasks list or create new:
<img src="https://user-images.githubusercontent.com/38133364/200641431-bf68c8c4-3964-46e2-9196-c0737af6c625.png" width=40% height=40%>

### Creating new list of tasks
<img src="https://user-images.githubusercontent.com/38133364/200642182-3f0aa410-dda8-4c68-8f6b-1ab106b648ba.png" width=40% height=40%>


### Setup of brothers and jobs before assignment
This step includes options to:
- Configure jobs and people (right click to rename or delete a job)
- Enable/disable people or jobs to exclude them from being assigned
- Directly assign someone a job
- Add a new job to the saved list
- Assign nicknames to differentiate people with the same name, or for fun
<img src="https://user-images.githubusercontent.com/38133364/200643761-91e5523d-82cd-4d44-8a90-0e8cbbd480ec.png" width=60% height=60%>

### Final output of assigned jobs
<img src="https://user-images.githubusercontent.com/38133364/200645926-29c7c034-3a0e-400b-9972-19fd29eff2ff.png" width=40% height=40%>
