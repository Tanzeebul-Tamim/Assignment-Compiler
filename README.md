# Assignment Compiler Project

### **Simplifying Assignment File Compilation:**

<break></break>

Welcome to the **Assignment Compiler Project** repository! This project was mainly focused on BRACU's CSE111 Programming Language II course's assignment submission, to help us ease the task of copying and pasting numerous assignment files into one text file, which is the only valid and accepted format of assignment submission for this course.

This project provides a utility to compile multiple assignment files into a single `.txt` file with proper formatting, adhering to university submission requirements. The program is designed to save time, ensure consistency, and reduce manual errors.

## Table of Contents

- [Project Features](#project-features)
- [Project Availability](#project-availability)
- [Used Packages and Technologies](#used-packages-and-technologies)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Program](#running-the-program)
- [Contributing](#contributing)
- [License](#license)

## Project Features

- **_File Compilation:_** Combines multiple assignment files into a single `.txt` file with proper formatting. Each task is separated by comments like `// Task 1`, `// Task 2`.

- **_Input Flexibility:_** The program allows you to specify which files to include in the compiled document.

- **_Automated Naming Convention:_** Automatically names the output file using a standard format (`Assignment XX_Your-ID_Your-Name.txt`).

- **_File Type Support:_** Supports multiple file extensions, including Java, Python, C, C++, HTML, CSS, JavaScript, TypeScript, and others. You can modify the supported file extensions directly in the code. The current supported extensions are:
  ```java
  String[] validExtensions = {
      "java", "txt", "py", "cpp", "c", "cs", "js", "ts", "html", "css", "xml", "json"
  };
  ```

- **_Subdirectory Handling:_** The program can process files in subdirectories, making it easier to handle projects with complex folder structures.

- **_Automated Task Sequence Detection:_** Automatically detects numbered task sequences (e.g., `Task 1`, `Task 2` or `task1`, `task2` or `Task-01`, `Task-02` etc.) from file names. If the sequence is not in order, you can manually input or reorder the tasks in the correct sequence.

- **_Error Handling:_** Validates input files to ensure they exist and are formatted correctly.

- **_Tailored for BRAC University:_** Specifically designed to assist students in BRAC University's **CSE111 - Programming Language II** course. The utility simplifies the task of combining multiple assignment files into the `.txt` format, which is the only valid and accepted format for assignment submissions in this course.

- **_Future GUI Support:_** Plans for a graphical user interface to simplify the process further.

## Project Availability

This project is currently a local utility tool and does not have a live deployment. Follow the instructions in the [Installation](#installation) and [Running the Program](#running-the-program) sections to use it.

## Used Packages and Technologies

- **Java**:
  - For the core implementation of the utility.
  - Utilized packages like `java.io.File` and `java.util.Scanner` for file handling and user input.
  
- **OOP Design Patterns**: Encapsulated file handling and processing logic for clean and modular code.

- **Command-Line Interface (CLI)**: Provides a simple interface to interact with the tool.

## Prerequisites

- JDK 11 or higher
- A text editor or IDE (e.g., IntelliJ IDEA, Eclipse, Visual Studio Code)

## Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/Tanzeebul-Tamim/Assignment-Compiler.git
   cd Assignment-Compiler
   ```

2. Create a `bin` directory to store compiled `.class` files:
   ```sh
   mkdir bin
   ```

3. Compile the Java source files:
   ```sh
   javac -d bin src/Main.java src/main/*.java
   ```

## Running the Program

1. Navigate to the `bin` directory:
   ```sh
   cd bin
   ```

2. Run the program:
   ```sh
   java Main
   ```

3. Follow the prompts to input file names and generate the compiled `.txt` file.

## Contributing

Contributions are welcome! Whether it's bug fixes, feature enhancements, or new ideas, feel free to fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

**Simplify your assignment compilation process and make your CSE111 submissions effortless!**