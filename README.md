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
- [Testing with Demo Files](#testing-with-demo-files)
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

<br>

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

- **_Manual Task Sequence Detection:_** If the program fails to detect a proper sequence or if your files are not named consistently, you’ll be prompted to manually provide the correct sequence for each file. This ensures that the tasks are ordered correctly before generating the final output.

- **_Combining Multiple Files as One Task:_** The program also allows you to combine multiple files into a single task in the generated output. If you have related files that should be considered part of the same task, you can group them together, and they will be treated as one task in the final document.

- **_Error Handling:_** Validates input files to ensure they exist and are formatted correctly.

- **_Empty Directory Detection:_** The program can detect and reject empty directories if provided, ensuring only valid directories containing files are processed.

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

    - **Enter the Assignment Number:**  
      You’ll be asked to input the assignment number, such as `3` for Assignment 03. The program supports assignment numbers ranging from `1` to `15`. Make sure to input a valid number within this range.

    - **Enter Your ID:**  
      You’ll then be prompted to enter your 8-digit student ID (e.g., `24100000`). The program verifies the ID to ensure a valid ID is provided.

    - **Enter Your Name:**  
      You’ll be then asked to input your name. The program automatically corrects improper naming conventions. For example, if you enter something like `tANzEEBuL       tAMiM    `, it will be converted to the proper format: `Tanzeebul Tamim`.

    - **Enter the File Extension:**  
      The program requires you to specify the desired file extension (e.g., `java`, `py`, `cpp`) to filter out unsupported or irrelevant files. It validates your input to ensure it matches one of the supported extensions and rejects any invalid or unsupported file types.

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

    - **Sequencing Options:**  
      At the start, you’ll be asked if you want to sequence or reorder your files:

      - **If you choose "Yes"**: The program will detect any existing sequence:

        - If all files are sequentially named (e.g., `Task 1`, `Task 2` or `task1`, `task2` or `Task-01`, `Task-02`), it will use this order.
        - If no sequence is found, you’ll be prompted to manually assign a sequence number to each file.
        - If some files have sequence numbers and others don’t, you’ll decide whether to resequence all files or only the ones without a sequence.

      - **If you choose "No"**: The program will generate the output using the file sequence as found in the file explorer.

      - **Combining Multiple Files as One Task**: If you have related files that should be treated as part of the same task, the program allows you to group them together, and they will be counted as a single task in the final output file.

    - **Important Considerations for File Organization:**  
      Before using the Assignment Compiler, ensure your files are properly organized for smooth processing. Here’s what to keep in mind:

      1. **Dedicated Folder for Assignment Files:**  
         Place all assignment-related files in a single folder. Avoid including unrelated files (e.g., images, documents) to prevent unnecessary errors.

      2. **Verify File Relevance:**  
         Double-check that all the files in the folder are part of your assignment. This reduces the risk of mistakenly including irrelevant or incomplete files in the output.

      3. **File Naming Conventions:**  
         For best results, use a consistent naming pattern like:

         - `Task-01`, `Task-02`, or
         - `task1`, `task2`, or
         - `Task_01`, `Task_02`.

         This naming convention allows the program to automatically detect the correct order of tasks.

      4. **Manual Sequencing (If Needed):**  
         If your files are not named sequentially or don’t follow a consistent pattern, the program will prompt you to manually arrange the task order. This ensures that your final output file is properly structured, sequenced, and adheres to the assignment submission requirements.

         > **Note:** In the OOP tasks assigned by BRAC university, class names often don’t follow any naming sequence. Instead, they use random names that are relevant to the task (e.g., `Circle`, `Employee`, `BankAccount`). This makes manual sequencing especially important to ensure that the tasks are arranged correctly before generating the output file.

Following these steps will ensure a smooth file compilation process.

</details>

---

<details>
  <summary><h2 id="testing-with-demo-files">Testing with Demo Files</h2></summary>

To help you test the program, a [**`Demo Folder`**](./demo) is included in the root directory.

**Demo Folder Contents:**
It contains:

- Sample files with supported extensions and various naming formats (e.g., [**`Task1.java`**](./demo/Task1.java), [**`task_03.java`**](./demo/task_03.java), [**`task__7.java`**](./demo/task__7.java)) to showcase the program's ability to detect sequences in different naming formats.
- Files without any numeric sequence to demonstrate how the program prompts for manual sequencing (e.g., [**`NonSequenced.java`**](./demo/NonSequenced.java), [**`Unsequenced.java`**](./demo/Unsequenced.java)).
- Files with unsupported extensions (e.g., [**`Unsupported.py`**](./demo/Unsupported.py), [**`Unsupported.ts`**](./demo/Unsupported.ts), [**`Unsupported.txt`**](./demo/Unsupported.txt)).
  > **Note:** Some files have supported extensions but are demonstrated as unsupported in the `Demo Folder` to illustrate that the `Demo Folder` is specifically designed for testing _**Java Files**_ as the desired file type.

**How to Use the Demo Folder:**

1. Navigate to the [**Demo Folder**](./demo) folder in the root directory.
2. Copy the path to the **Demo Folder** folder and paste it when prompted during program execution.
3. Test the program's functionality with the pre-included sample files to familiarize yourself with its features.

Feel free to modify or add your own files to the **Demo Folder** to test with your own files.

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
