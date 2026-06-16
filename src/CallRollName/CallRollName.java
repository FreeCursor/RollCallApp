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
    private static ArrayList<String> nameList = new ArrayList<>();

    public static void main(String[] args) {
        // 1. 从外部 names.txt 文件读取名单
        loadNamesFromFile("CallRollName/names.txt");

        // 2. 创建窗口地基
        JFrame frame = new JFrame("班级随机点名程序 🎲");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // 3. 创建显示名字的标签
        JLabel nameLabel = new JLabel("准备点名", SwingConstants.CENTER);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));

        // 4. 创建控制按钮
        JButton startButton = new JButton("开始点名 🎯");
        startButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));

        // 5. 核心：为按钮添加点击事件监听器 (AP CSA 核心概念：接口与事件驱动)
        Random random = new Random();
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameList.isEmpty()) {
                    nameLabel.setText("名单为空！");
                    return;
                }
                // 经典 AP CSA 考点：通过 Math.random() 或 Random 类获取随机索引
                // 索引范围是 [0, nameList.size() - 1]
                int randomIndex = random.nextInt(nameList.size());
                String luckyStudent = nameList.get(randomIndex);

                // 将抽中的名字显示在界面上
                nameLabel.setText(luckyStudent);
            }
        });

        // 6. 组装并显示
        frame.add(nameLabel, BorderLayout.CENTER);
        frame.add(startButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * AP CSA 考点：使用 Scanner 读取文件与异常处理 (Try-Catch)
     */
    private static void loadNamesFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // 循环读取文件的每一行，直到文件末尾
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    nameList.add(name); // 动态添加到 ArrayList 中
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("找不到名单文件: " + fileName + "，请确保它在项目根目录下。");
            // 如果文件不存在，先放几个备用名字防止程序崩溃
            nameList.add("同学A");
            nameList.add("同学B");
            nameList.add("同学C");
        }
    }
}