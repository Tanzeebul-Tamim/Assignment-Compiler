<h1 align=center>Assignment Compiler Project</h1>
<h4 align=center>Simplify your assignment compilation process and make your CSE111 submissions effortless!</h4>

---

<break></break>

Welcome to the **Assignment Compiler Project** repository! This project is mainly focused on BRACU's CSE111 Programming Language II course's assignment submission, to help us ease the task of copying and pasting numerous assignment files into one text file, which is the only valid and accepted format of assignment submission for this course.

This project provides a utility to compile multiple assignment files into a single `.txt` file with proper formatting, adhering to university submission requirements. The program is designed to save time, ensure consistency, and reduce manual errors.

## Table of Contents

- [Project Availability](#project-availability)
- [Used Packages and Technologies](#used-packages-and-technologies)
- [Prerequisites](#prerequisites)
- [Project Features](#project-features)
- [Installation](#installation)
- [Running the Program](#running-the-program)
- [Demo Output File Example](#demo-output-file-example)
- [Contributing](#contributing)
- [License](#license)

## Project Availability

This project is currently a local utility tool and does not have a live deployment. Follow the instructions in the [Installation](#installation) and [Running the Program](#running-the-program) sections to use it.

## Used Packages and Technologies

- **Java**:

  - For the core implementation of the utility.
  - Utilized packages for file handling:
    - **`java.io.File`**: To handle file paths, check file existence, and create files or directories.
    - **`java.util.Scanner`**: For reading file contents and accepting user inputs during runtime.
    - **`java.io.FileWriter` and `java.io.BufferedWriter`**: For writing formatted outputs to a text file.
  - **`java.util.concurrent.atomic.AtomicInteger`**: For thread-safe counting of task numbers during file processing.

- **OOP Design Patterns**: Encapsulated file handling and processing logic for clean and modular code.

- **Command-Line Interface (CLI)**: Provides a simple interface to interact with the tool.

## Prerequisites

- JDK 11 or higher
- A text editor or IDE (e.g., IntelliJ IDEA, Eclipse, Visual Studio Code)

---
<details>
<summary><h2 id="project-features">Project Features</h2></summary>

- **_File Compilation:_** Combines multiple assignment files into a single `.txt` file with proper formatting. Each task is separated by comments like `// TASK 1`, `// TASK 2`.

- **_Input Flexibility:_** The program allows you to specify which files to include in the compiled document.

- **_Automated Naming Convention:_** Automatically names the output file using a standard format (`Assignment XX_Your-ID_Your-Name.txt`).

- **_File Type Support:_** Supports multiple file extensions, including Java, Python, C, C++, HTML, CSS, JavaScript, TypeScript, and others. You can modify the supported file extensions directly in the code. The current supported extensions are:

  - js
  - ts
  - html
  - css
  - xml
  - txt
  - java
  - py
  - cpp
  - c
  - cs

- **_Subdirectory Handling:_** The program can process files in subdirectories, making it easier to handle projects with complex folder structures. However, it is **highly recommended** to keep all files in the main directory of the provided path, as issues might occur if files are spread across multiple subdirectories.

- **_Automated Task Sequence Detection:_** Automatically detects numbered task sequences (e.g., `Task 1`, `Task 2` or `task1`, `task2` or `Task-01`, `Task-02` etc.) from file names. If the sequence is not in order, you can manually input or reorder the tasks in the correct sequence.

- **_Error Handling:_** Validates input files to ensure they exist and are formatted correctly.

- **_Handling Files with the Same Name:_** If a file with the same name already exists in the directory, the program provides 3 options to the user:

  - **Overwrite:** Replace the existing file with the new one.
  - **Create New Version:** Generate a new version of the file with a suffix like `(1)`, `(2)`, etc., appended to its name.
  - **Skip:** Skip the current file writing operation entirely.

- **_Tailored for BRAC University:_** Specifically designed to assist students in BRAC University's **CSE111 - Programming Language II** course. The utility simplifies the task of combining multiple assignment files into the `.txt` format, which is the only valid and accepted format for assignment submissions in this course.

- **_Future GUI Support:_** Plans for a graphical user interface to simplify the process further.
</details>

---
<details>
  <summary><h2 id="installation">Installation</h2></summary>

1. **Clone the repository:**

   ```sh
   git clone https://github.com/Tanzeebul-Tamim/Assignment-Compiler.git
   cd Assignment-Compiler
   ```

2. **Create a `bin` directory to store compiled `.class` files:**

   ```sh
   mkdir bin
   ```

3. **Compile the Java source files:**
   ```sh
   javac -d bin src/Main.java src/utilities/*.java
   ```
   </details>

---
<details>
  <summary><h2 id="running-the-program">Running the Program</h2></summary>

1.  **Navigate to the `bin` directory:**

    ```sh
    cd bin
    ```

2.  **Run the program:**

    ```sh
    java Main
    ```

3.  **Follow the prompts:**

    When you run the program, you’ll be guided through several input steps. Here's what to expect:

    - **Enter Your Name:**  
      You’ll be asked to input your name. The program automatically corrects improper naming conventions. For example, if you enter something like `tANzEEBuL       tAMiM    `, it will be converted to the proper format: `Tanzeebul Tamim`.

    - **Enter Your ID:**  
      You’ll then be prompted to enter your 8-digit student ID (e.g., `24100000`). The program verifies the ID to ensure a valid ID is provided.

    - **Enter the Assignment Number:**  
      You’ll be asked to input the assignment number, such as `3` for Assignment 03. The program supports assignment numbers ranging from `1` to `15`. Make sure to input a valid number within this range.

    - **Enter the Path to Your Assignment Folder:**  
       The program requires the folder path where your assignment files are stored. You can copy the file path directly from your file explorer:

      <h4>Windows:</h4>
      <img align=center src="./public/windows-copy-path.jpg" alt="Windows Screenshot" width="500px">

      - Right-click the folder containing your assignment files in your file explorer.
      - Select **"Copy as path"** to copy the full folder path.
      - (e.g., `C:\Users\Tamim\Documents\Assignments\OOP_Tasks`)

      <h4>macOS:</h4>
      <img align=center src="./public/macos-copy-path.jpg" alt="macOS Screenshot" width="500px">

      - Right-click the folder containing your assignment files in Finder.
      - Select **"Copy 'FolderName' as Pathname"** to copy the path directly.
      - (e.g., `/Users/tamim/Documents/Assignments/OOP_Tasks`)

      <h4>Linux:</h4>
      <img align=center src="./public/linux-copy-path.jpg" alt="Linux Screenshot" width="500px">

      - Right-click the folder containing your assignment files in your file manager (e.g., KDE Dolphin).
      - Select **"Copy Location"** to copy the full folder path.
      - (e.g., `/home/tamim/Documents/Assignments/OOP_Tasks`)

      After copying the path, paste it directly into the program's terminal/console.

    - **Important Considerations for File Organization:**  
      When using the Assignment Compiler, it’s crucial to ensure your files are properly organized before you input the directory path. Here’s a breakdown of the key points:

      1.  **Dedicated Folder for Assignment Files:**  
          Place all your assignment files in a single, dedicated folder. Avoid including unrelated files such as documents, images, or irrelevant executables in the same directory. This helps the program focus only on the relevant files and prevents unnecessary processing errors.

      2.  **Verify File Relevance:**  
          Double-check that all the files in the folder are part of your assignment. This reduces the risk of mistakenly including irrelevant or incomplete files in the output.

      3.  **File Naming Sequence:**  
          For best results, name your files in a sequential order, such as:

          - `Task-01`, `Task-02`, etc.
          - Or `task1`, `task2`, etc.
          - Even `Task_01`, `Task_02` works.

          This naming convention allows the program to automatically detect the correct order of tasks.

      4.  **Manual Sequencing (If Needed):**  
          If your files are not named sequentially or don’t follow a consistent pattern, the program will prompt you to manually arrange the task order. This ensures that your final output file is properly structured, sequenced, and adheres to the assignment submission requirements.

          > **Note:** In the OOP tasks assigned by our university, class names often don’t follow any naming sequence. Instead, they use random names that are relevant to the task (e.g., `Circle`, `Employee`, `BankAccount`). This makes manual sequencing especially important to ensure that the tasks are arranged correctly before generating the output file.

Following these steps will ensure a smooth file compilation process.

</details>

---
<details>
  <summary><h2 id="demo-output-file-example">Demo Output File Example</h2></summary>

Here is a sample output generated by the program:

### File Name:

Assignment 03_24100000_Tanzeebul Tamim.txt

### File Content:

```
// TASK 1
public class Task1 {
    public static void main (String [] args) {
         //This is a DEMO
        System.out.println("Task 1");
    }
}


// TASK 2
public class Task2 {
    public static void main (String [] args) {
         //This is a DEMO
        System.out.println("Task 2");
    }
}


// TASK 3
public class Task3 {
    public static void main (String [] args) {
         //This is a DEMO
        System.out.println("Task 3");
    }
}
```

This sample demonstrates how tasks are numbered and formatted consistently, ensuring compliance with BRAC University's assignment submission requirements. The program handles task numbering, file naming, and proper organization for effortless submissions.

</details>

---

<br>

## Contributing

Contributions are welcome! Whether it's bug fixes, feature enhancements, or new ideas, feel free to fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
