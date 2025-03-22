# Vancouver Bus Management System

<img align="right" src="https://i.imgur.com/BweSxMm.png" width="300" height="220">
<p align=justify>
  This is our <strong>Algorithms and Data Structures II Group Project 2020/21</strong>, implementing a <strong>bus management system</strong>. For development purposes, the root of this repository is IntelliJ IDEA and Eclipse IDE friendly (the associated files/folders are in the .gitignore) so feel free to keep your IntelliJ or Eclipse project in your local version of the repository. If IntelliJ or Eclipse create any extra project-specific files, please add them to the .gitignore file. However, for production purposes <strong>do not</strong> run this application with the command line on Eclipse, as there are some <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=76936">Eclipse bugs</a> which do not render the progress bars in the application. We strongly recommend that you run this application from a <i>regular command line or with the console on IntelliJ.</i>
</p>

## What features does this project implement?

  1. It can find the shortest paths between 2 bus stops inputted by the user, returning the list of stops en route as well as the associated “cost”.
  2. One can search for a bus stop by full name or by the first few characters in the name using a ternary search tree (TST), returning the full stop information for each stop matching the search criteria.
  3. One can search for all trips with a given arrival time, returning full details of all trips matching the criteria sorted by trip id.
  4. A front interface enabling selection for features 1-3 with an option to exit the program and enabling required user input with functionality/error checking.

## Group Members

|         Name          |           Course            |                      GitHub Username                     |
|:---------------------:|:---------------------------:|:--------------------------------------------------------:|
| Michael Makarenko     | Integrated Computer Science | [makarenm][makarenm]                                     |
| Adam Bewick Mulvihill | Integrated Computer Science | [A-Mulvihill][A-Mulvihill]                               |
| Prathamesh Sai        | Integrated Computer Science | [saisankp][saisankp]                                     |
| Daniel Ilyin          | Integrated Computer Science | [danieli1245][danieli1245]                               |

## Group Housekeeping

<p align=justify>
  For communication, we have a group discord server to discuss the project with each other. On a weekly basis, we update the files <a href="https://github.com/Zugidor/TCD-Algos-2021/blob/main/TODO.md"><i>TODO.md</i></a> and <a href="https://github.com/Zugidor/TCD-Algos-2021/blob/main/Progress.md"><i>Progress.md</i></a> to keep track of the work we need to do, and to also keep track of who did what. For more information on the contributions from each member of the team, please refer to <a href="https://github.com/Zugidor/TCD-Algos-2021/graphs/contributors">this repository's contributions page</a>.
</p>

## View previous versions of the Design Document

<p align=justify>
  We have made a design document explaining the design decisions we made. This includes describing the choice of data structures and algorithms we used to
  implement each of the features in this project, and also justifying them based on specific space/time trade-offs
  between alternatives we have considered. To view the previous versions of this document we have developed over the course of the semester, follow these steps.

  1. Click on the <a href="https://docs.google.com/document/d/1hChR0j6R_rrh9twIer2SVZvPdkKD8LXY2jtnjXIEBP0/edit?usp=sharing">Google Docs link for the design document</a>.
  2. At the top left-hand corner, click on <b>File</b> > <b>Version History</b> > <b>See version history</b>.
  3. You will now be able to see all previous versions of the design document on the right-hand side of the page.

</p>

## View a demonstration of this project

To view the demo video for our project, click the video below. Alternatively, you can also download it locally from the <a href="https://github.com/Zugidor/TCD-Algos-2021/tree/main/demo">`./demo`</a> directory or watch it on [YouTube][YouTubeDemo].

<https://user-images.githubusercontent.com/34750736/116626608-71147080-a943-11eb-9952-46d58057e2ee.mov>

[Zugidor]: https://github.com/zugidor
[A-Mulvihill]: https://github.com/A-Mulvihill
[saisankp]: https://github.com/saisankp
[danieli1245]: https://github.com/danieli1245
[EclipseConsoleBug]: https://bugs.eclipse.org/bugs/show_bug.cgi?id=76936
[YouTubeDemo]: https://www.youtube.com/watch?v=F8svyrnqf5A&ab_channel=PrathameshSaiSankar
