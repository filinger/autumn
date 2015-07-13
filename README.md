# autumn
Small application configuration framework, compatible with Android. Think of it as a mini-Spring. Currently it supports configurations written in YAML, using special properties to instantiate new beans, inject properties etc. Annotation-driven configuration will be added soon.

# autumn-core
List of the special properties:
	$int: 42 # indicates that the specified string should be parsed to Integer
    $float: 34.69 # parses value to Float
    $bool: true # treats value as Boolean
	$import: com.technoirarts.autumn.bean # indicates that the specified string should be treated as package name and imported into the configuration context.
    $class: com.technoirarts.autumn.bean.TestBean # converts classname value to class; uses packages that were imported with '$import' property
    $new: java.lang.Integer # treats value as classname and tries to make a new instance of that Class; rest of the properties are considered constructor arguments (see [autumn-yaml](README.md#autumn-yaml)
    
    

# autumn-yaml
Example given:
	imports: # instantiates a new map with id 'imports'
      $import: com.technoirarts.autumn.bean # '$import' property indicates that the specified package should be imported 
    ---
    simpleBean:
      $bean: { $new: SimpleTestBean }
      name: SimpleTestBean
      scores: [1, 2, 3]
    ---
    closedBean:
      $bean: { $new: ClosedTestBean }
      name: ClosedTestBean
    ---
    immutableBean:
      $bean:
        $new: ImmutableTestBean
        0: ImmutableTestBean
        1: { $int: 42 }
        2: { $inject: closedBean }
    ---
    beanCollection:
      $bean: { $new: TestBeanCollection }
      allTestBeans: { $inject: TestBean }
      
