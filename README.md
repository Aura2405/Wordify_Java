# Wordify — A Wordle-like Game in Java

**Wordify** is a fun clone of the popular Wordle game, implemented using Java Swing for a simple and interactive GUI.

---

## Features
- Word guessing game with limited attempts
- Color-coded feedback (like Wordle: green/yellow/grey)
- Simple graphical interface
- Lightweight and fast

---

## How to Build & Run

### Run the prebuilt `.jar` (recommended)
You can simply download and run the precompiled JAR file:

```bash
java -jar WordifyGame.jar
```
## Build From Source

### Compile
```bash
javac -d bin -sourcepath src src/com/example/WordifyGame.java
```

### Run
```bash
java -cp bin com.example.WordifyGame
```

## Project Structure
```pgsql
Wordify/
├── manifest.txt
├── Wordify.jar
├── src/
│   └── com/
│       └── example/
│           ├── WordifyGame.java
│           ├── WordifyGame.class
│           └── (other files)
├── 5-letter-words.txt
├── bin/             (generated after compilation)
├── .gitignore
README.md
LICENSE

```
## Technologies
- Java 8+
- Swing GUI
