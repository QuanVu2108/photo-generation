# photo-generation
using free with https://deepai.org/machine-learning-model/text2img 

Example: 
gen image: curl --location 'http://localhost:8094/api/v0/image-generation?code={code}&prompt={prompt}

get image by code: curl --location 'http://localhost:8094/api/v0/image-generation/{code}'
