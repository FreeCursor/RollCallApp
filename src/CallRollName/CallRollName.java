package CallRollName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CallRollName {
    // 使用 ArrayList 存储名字，方便动态读取
    private static final ArrayList<String> nameList = new ArrayList<>();

//     public static void main(String[] args) {     public is redundant
         static void main(){
        // 1. get nameList from  names.txt
        loadNamesFromFile("./names.txt");
        // 2. create window
        JFrame frame = new JFrame("班级随机点名程序 🎲");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 3. show name
        JLabel nameLabel = new JLabel("准备点名", SwingConstants.CENTER);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));

        // 4. button
        JButton startButton = new JButton("开始点名 🎯");
        startButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));

        // 5. read list
        Random random = new Random();
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameList.isEmpty()) {
                    nameLabel.setText("名单为空！");
                    return;
                }

                // range [0, nameList.size() - 1]
                int randomIndex = random.nextInt(nameList.size());
                String luckyStudent = nameList.get(randomIndex);

                // display
                nameLabel.setText(luckyStudent);
            }
        });

        // 6. assembly
        frame.add(nameLabel, BorderLayout.CENTER);
        frame.add(startButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // reading ..
    private static void loadNamesFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);


            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    nameList.add(name); // 动态添加到 ArrayList 中
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("找不到名单文件: " + fileName + "，请确保它在项目根目录下。");

            nameList.add("同学A");
            nameList.add("同学B");
            nameList.add("同学C");
        }
    }
}