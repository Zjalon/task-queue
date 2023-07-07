# task-queue
java 任务队列
- 加入依赖
```

<dependency>
    <groupId>com.jalon</groupId>
    <artifactId>task-queue</artifactId>
    <version>1.0.0</version>
</dependency>
```
- 使用方法
```
// 注入
@Autowired
private TaskService taskService;
```
```
// 继承 AbstractTask
public class TaskTest extends AbstractTask {
    @Override
    public void run() {
        setMessage("kaishi");
    }
}
```
```
// 将任务加入队列
TaskTest test = new TaskTest();
taskService.addTask(test);
```