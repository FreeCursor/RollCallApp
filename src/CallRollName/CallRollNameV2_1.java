package CallRollName;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class CallRollNameV2_1 {
    private static final ArrayList<String> nameList = new ArrayList<>();
    private static final Random random = new Random();
    private static int clickCount = 0; // record times

    public static void main(String[] args) {
        loadNamesFromFile();

        JFrame frame = new JFrame("智能滚动点名系统 v2.0 ");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("准备点名", SwingConstants.CENTER);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 38));

        JButton startButton = new JButton("开始点名 ");
        startButton.setFont(new Font("微软雅黑", Font.PLAIN, 18));

        // main codes
        startButton.addActionListener(e -> {
            // 1. reset list 
            if (nameList.isEmpty()) {
                loadNamesFromFile();
                if (nameList.isEmpty()) {
                    nameLabel.setText("名单文件为空！");
                    return;
                }
                JOptionPane.showMessageDialog(frame, " 全班已轮完一遍，自动重置名单！");
            }

            // 2. 禁用按钮，防止滚动期间被重复点击
            startButton.setEnabled(false);

            // 3. 聪明的办法：复制一个用于滚动展示的临时名单
            ArrayList<String> temp = new ArrayList<>(nameList);

            // 4. 创建定时器实现疯狂滚动特效（每50毫秒刷新一次）
            javax.swing.Timer timer = new javax.swing.Timer(50, null);
            final int[] counter = {0}; // 闪烁次数计数器

            timer.addActionListener(evt -> {
                if (counter[0] < 20) { // 闪烁20次（约1秒）
                    int randomIndex = random.nextInt(temp.size());
                    nameLabel.setText(temp.get(randomIndex)); // 从临时名单拿名字展示
                    counter[0]++;
                } else {
                    // 5. 滚动结束，关闭闹钟
                    timer.stop();

                    // 6. 从真正的主卡池中移出幸运儿，确保不重复
                    int finalIndex = random.nextInt(nameList.size());
                    String luckyStudent = nameList.remove(finalIndex);

                    // 7. 定格最终结果并恢复按钮
                    nameLabel.setText(luckyStudent);
                    startButton.setEnabled(true);

                    // 8. 触发隐藏任务：保存点名记录
                    saveToHistory(luckyStudent);
                }
            });

            timer.start(); // 启动定时器
        });

        frame.add(nameLabel, BorderLayout.CENTER);
        frame.add(startButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    // 加载名单
    private static void loadNamesFromFile() {
        try {
            File file = new File("./names.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine().trim();
                if (!name.isEmpty()) {
                    nameList.add(name);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("未找到 names.txt，加载默认名单");
            nameList.add("测试同学A");
            nameList.add("测试同学B");
            nameList.add("测试同学C");
        }
    }

    //  保存记录到本地文件的聪明函数
    private static void saveToHistory(String name) {
        try {
            // 使用追加模式（true）打开文件写入器
            FileWriter writer = new FileWriter("./history.txt", true);

            // 获取当前系统时间并格式化
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timeStr = now.format(formatter);

            clickCount++;
            // 写入记录：[第几次] 时间 - 名字
            writer.write("[" + clickCount + "] " + timeStr + " -> " + name + "\n");
            writer.close(); // 记得关闭流
        } catch (IOException e) {
            System.out.println("保存历史记录失败");
        }
    }
}