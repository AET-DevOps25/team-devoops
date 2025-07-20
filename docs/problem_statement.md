# 🍲 Meet@Mensa

![Meet@Mensa logo](resources/img/meet@mensa.png "Meet@Mensa")

Many students at TUM, particularly in the larger study programs, report feeling lonely and isolated despite having countless peers. One of the places where this is especially noticeable is the Mensa during lunchtime. It's common for students that don't have a strong network of friends at university to eat alone or avoid eating at the Mensa entirely to not feel socially awkward.

Our tool - *Meet@Mensa* - focuses on transforming the potentially uncomfortable situation of eating alone at the Mensa into an opportunity to meet fellow students and make new friends. In order to enable this, our tool offers the following features:

### 📝 - Signing Up
In order to match students, our tool needs to gather some basic information about them first. In order to do this, users can specify what times they plan to have lunch and at which Mensa, as well as some personal data, such as their name, age, language, gender, study program, current semester, courses they are taking, and hobbies they enjoy. All personal data fields are optional. 

### ✨ - Matching
Our system randomly matches students into groups of 4 exactly 1 hour before their common lunchtime, and invites them to share a table. If the number of requests for a certain time slot is not divisible by 4, an additional group of 2-3 students is formed. If only 1 student is left out, they are added on top of a 4-student group. 

Users can also add preferences to their search by setting filters, e.g. if they are willing to meet up with people from the same degree. However, setting filters does not guarantee a 100% match - our system prioritises assigning everyone to a group one way or another over meeting everyone's preferences.

### 🤝 - RSVP-ing
Nothing is worse than waiting for someone who won't show up, thus our tool asks users to RSVP to any invites they receive (with ```yes``` / ```no``` options, no tentatives). Once an invite is accepted, all users receive a confirmation email with a corresponding ```ics.``` file in the attachment. It is also possible to cancel the meetup.

### 🤸 - Meeting up
Students at the same table should also have something to talk about, so our system's GenAI component will suggest topics based on the students' common interests to help break the ice.

## ⚙ System Overview

### Class Diagram (analysis object model)

![Class Diagram](resources/diagrams/meetatmensa_uml_class.png "Meet@Mensa Class Diagram")

### Use Case Diagram

![Use-Case Diagram](resources/diagrams/meetatmensa_uml_use_case.png "Meet@Mensa Use-Case Diagram")

### Component Diagram (top-level architecture)

![Component Diagram](resources/diagrams/meetatmensa_uml_component.png "Meet@Mensa Component Diagram")

## 🎭 Use Case Scenarios

### Scenario 1
James is a first-semester M.Sc. Informatics student. Unfortunately, he wasn't able to be in Munich for the SET in the first few weeks and thus hasn't really made any friends.

James is comfortable eating alone at the Mensa, but he'd like to meet some of his fellow students, so he logs onto Meet@Mensa and inputs the times he plans to have lunch at the Garching Mensa, what degree he is studying, what classes he's taking, and what his hobbies are outside of university. James doesn't have any preferences on whom to meet, so he sets no search criteria and leaves it to chance.

On Monday, an hour before lunchtime, James receives a notification that he's been matched with a table of 3 other students for lunch at the Mensa. James accepts the invitation, letting the system know he plans on joining.

At the agreed time, James arrives at the Mensa, gets his food and joins the other students at the table. Not knowing how where to begin, he checks the list of conversation starters and sees that several students are also taking the DevOps course this semester. With this in mind, James asks the other students about their projects and exchanges some jokes about Scrum masters.

After an hour of pleasant conversation, James has now met several new people and goes about his day feeling less lonely.

---
### Scenario 2
Anastasiia is also a first-semester M.Sc. Informatics student. She met a lot of wonderful people during the orientation week but noticed there were barely any female students in her degree. Being new to Munich and wanting to connect with other women in Computer Science, Anastasiia signs up for Meet@Mensa.

She fills out the information about her studies and hobbies, and specifies she would like to have lunch with other female students in their 1st semester. For this, she activates 2 filters in the search criteria: gender and current semester. One hour before her lunchtime on Friday, she receives a notification that she has been matched with 1 student. Anastasiia reviews her profile and notices they both have a lot of different hobbies they could tell each other about. For example, Anastasiia's match is passionate about horse riding, which Anastasiia herself always wanted to learn more about.

Anastasiia loves diversity and meeting people from different backgrounds, so she happily accepts the invitation. The meeting goes really well, and both students instantly click, so they agree to meet up for lunch again next week. The system has brilliantly fulfilled its purpose.

---
### Scenario 3
Enrico is a second-semester M.Sc. student in Informatics, and he's currently looking for group members for his Interdisciplinary Project. While most of his classmates are in Computer Science, he's interested in meeting students from other departments to bring diverse perspectives to the project.

He signs into Meet@Mensa and indicates that he's particularly looking for people involved in the Interdisciplinary Project this semester. He selects lunchtime on Thursday and fills out his profile with relevant classes and hobbies, including his passion for design and urban mobility.

Enrico is matched with 2 other students, all of them accept the invitation.

During lunch, one of the AI-generated conversation starters suggests discussing project ideas around sustainability, a topic all three are passionate about. The group ends up brainstorming potential concepts, and by 