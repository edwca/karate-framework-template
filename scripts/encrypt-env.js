const fs = require('fs');
const crypto = require('crypto');
const path = require('path');

const secret = process.env.ENV_SECRET_KEY;
if (!secret) throw new Error('❌ ENV_SECRET_KEY no definida');

const key = crypto.createHash('sha256').update(secret).digest();
const algorithm = 'aes-256-cbc';

const encrypt = (buffer) => {
  const iv = crypto.randomBytes(16);
  const cipher = crypto.createCipheriv(algorithm, key, iv);
  const encrypted = Buffer.concat([cipher.update(buffer), cipher.final()]);
  return Buffer.concat([iv, encrypted]);
};

const files = ['env.qa.json', 'env.devel.json', 'env.pre-prod.json'];

files.forEach(file => {
  const inputPath = path.join('.', file);
  const outputPath = inputPath + '.enc';
  const data = fs.readFileSync(inputPath);
  const encrypted = encrypt(data);
  fs.writeFileSync(outputPath, encrypted);
  console.info(`🔒 ${file} cifrado como ${path.basename(outputPath)}`);
});
