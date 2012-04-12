assert defaultQueue

request.queueName = defaultQueue.queueName

assert queues

def accessQueue = queues.default
request.someQueue = accessQueue.queueName
def task = accessQueue.add()
request.task = task.name
accessQueue.deleteTask(task)
