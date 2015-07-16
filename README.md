# About
Small application configuration framework, compatible with Android. Think of it as a mini-Spring. Currently it supports configurations written in YAML, using special properties to instantiate new beans, inject properties etc. Annotation-driven configuration will be added soon.

# Usage
## Maven
```xml
<repositories>
  <repository>
    <id>central</id>
    <name>bintray</name>
    <url>http://jcenter.bintray.com</url>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>com.technoirarts.autumn</groupId>
    <artifactId>autumn-yaml</artifactId>
    <version>0.1.1c</version>
  </dependency>
</dependencies>
```
## Gradle
```gradle
repositories {
    jcenter()
}

dependencies {
    compile 'com.technoirarts.autumn:autumn-yaml:0.1.1c'
}
```
## Java
### Beans
```java
public interface TestBean {
}
```
```java
public class SimpleTestBean implements TestBean {

    private String name;
    private List<Integer> scores;
}
```
```java
public class ImmutableTestBean implements TestBean {

    private final String name;
    private final Integer age;
    private final TestBean relative;

    public ImmutableTestBean(String name, Integer age, TestBean relative) {
        this.name = name;
        this.age = age;
        this.relative = relative;
    }
}
```
### Context
```java
InputStream stream = getClass().getClassLoader().getResourceAsStream("simple-test-yaml-config.yaml");
Reader reader = new InputStreamReader(stream);
ApplicationContext context = new SimpleYamlApplicationContext(reader);
        
context.loadAll();

Object someSimpleBean = context.getBean("someSimpleBean"); // gets bean instance by id
SimpleTestBean simpleTestBean = context.getBean(SimpleTestBean.class); // gets bean instance by class

assert someSimpleBean == simpleTestBean;

List<TestBean> allTestBeans = context.getBeans(TestBean.class); // gets all bean instances of class

assert allTestBeans.size() == 2;
```
## Yaml
```yaml
someSimpleBean:
  $bean: { $new: com.technoirarts.autumn.bean.SimpleTestBean }
  name: SimpleTestBean
  scores: [1, 2, 3,]

otherImmutableBean:
  $bean:
    $new: com.technoirarts.autumn.bean.ImmutableTestBean
    0: ImmutableTestBean
    1: { $int: 42 }
    2: { $inject: someSimpleBean }
```
# Details
## autumn-core
List of the special properties:
```yaml
$int: 42
# indicates that the specified string should be parsed to Integer

$float: 34.69
# parses value to Float

$bool: true
# treats value as Boolean

$bean: { $int: 42 }
# indicates that the entity should be treated as a bean instance; rest of the properties are considered bean properties

$import: com.technoirarts.autumn.bean
# indicates that the specified string should be treated as package name and imported into the configuration context

$class: com.technoirarts.autumn.bean.TestBean
# converts classname value to class; uses packages that were imported with '$import' property

$new: java.lang.Integer
# treats value as a classname and tries to make a new instance of that Class; rest of the properties are considered constructor arguments (see **autumn-yaml** for example)

$inject: someOtherBean
# this is a versatile property which will treat value either as a bean id, or a bean class, and will try to reference those bean(s)
```

## autumn-yaml
Example given:
```yaml
imports: # describes a new entity with id 'imports'
  $import: com.technoirarts.autumn.bean # imports package 'com.technoirarts.autumn.bean'

--- # marks new document start, thus forcing **autumn** to process what was described in previous document; useful for bean injection (i.e. _describe dependencies first_)

simpleBean:              # describes a new entity with id 'simpleBean'
  $bean:                 # tells to treat this entity as a bean of:
    $new: SimpleTestBean # new SimpleTestBean();
  name: SimpleTestBean   # simpleBean.setName("SimpleTestBean");
  scores: [1, 2, 3]      # simpleBean.setScores(new int[]{1, 2, 3});

closedBean:
  $bean: { $new: ClosedTestBean } # new ClosedTestBean();
  name: ClosedTestBean            # will set the property even if it has no public access

---

immutableBean:
  $bean:
    $new: ImmutableTestBean    # new ImmutableTestBean( // new instance of ImmutableTestBean
    0: ImmutableTestBean       # "ImmutableTestBean", // first constructor argument
    1: { $int: 42 }            # new Integer(42), // second constructor argument, which is described by another special property '$int', yielding us new Integer
    2: { $inject: closedBean } # closedBean); // third constructor argument, wich is described by '$inject', thus giving us a reference to previously described 'closedBean' entity

---

beanCollection:
  $bean: { $new: TestBeanCollection } # new TestBeanCollection();
  allTestBeans: { $inject: TestBean } # beanCollection.allTestBeans = new TestBean[]{ simpleBean, closedBean, immutableBean } // '$inject' will first try to find a bean by specified id, if no such bean is found it will try to satisfy the dependency by class
```
