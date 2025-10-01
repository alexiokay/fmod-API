<template>
  <div class="block-builder-container">
    <ClientOnly>
      <TresCanvas v-bind="gl">
        <!-- Camera -->
        <TresPerspectiveCamera :position="[20, 15, 20]" :look-at="[0, 0, 0]" />

        <!-- Sky -->
        <TresMesh>
          <TresSphereGeometry :args="[100, 32, 32]" />
          <TresMeshBasicMaterial
            :color="isDark ? 0x0f0f23 : 0x87ceeb"
            :side="1"
          />
        </TresMesh>

        <!-- Stars for night -->
        <template v-if="isDark">
          <TresMesh
            v-for="(star, index) in stars"
            :key="index"
            :position="star.position"
          >
            <TresSphereGeometry :args="[star.size, 8, 8]" />
            <TresMeshBasicMaterial
              :color="0xffffff"
              :transparent="true"
              :opacity="star.currentOpacity"
            />
          </TresMesh>
        </template>

        <!-- Lighting -->
        <TresDirectionalLight
          :position="[50, 80, 30]"
          :intensity="isDark ? 0 : 1.5"
          :cast-shadow="true"
          :color="isDark ? 0x4a5568 : 0xffeaa7"
        />

        <TresDirectionalLight
          v-if="isDark"
          :position="[-80, 100, -40]"
          :intensity="0.8"
          :cast-shadow="true"
          :color="0xb3d9ff"
        />

        <TresAmbientLight
          :intensity="isDark ? 0.2 : 0.3"
          :color="isDark ? 0x1a1a2e : 0x87ceeb"
        />

        <TresHemisphereLight
          :sky-color="isDark ? 0x0a0a1a : 0x87ceeb"
          :ground-color="isDark ? 0x1a1a1a : 0x8b6914"
          :intensity="isDark ? 0.3 : 0.4"
        />

        <!-- Custom block structure - Build your own! -->
        <TresGroup :position="[0, 0, 0]">
          <template v-for="(block, index) in customStructure" :key="index">
            <!-- Grass blocks -->
            <TresMesh
              v-if="block.type === 'grass'"
              :position="block.position"
              :scale="[2, 2, 2]"
              :receive-shadow="true"
              :cast-shadow="true"
              :material="grassBlockMaterials"
            >
              <TresBoxGeometry :args="[1, 1, 1]" />
            </TresMesh>

            <!-- Animated speaker cone blocks (bass reactive) -->
            <TresMesh
              v-else-if="block.isSpeakerCone"
              :position="block.position"
              :scale="block.animatedScale"
              :receive-shadow="true"
              :cast-shadow="true"
              @click="toggleAudio"
            >
              <TresBoxGeometry :args="[1, 1, 1]" />
              <TresMeshStandardMaterial
                :map="getBlockTexture(block.type)"
                :roughness="0.9"
                :metalness="0.0"
                :emissive="isPlaying ? 0x443300 : 0x000000"
                :emissiveIntensity="isPlaying ? bassLevel * 2 : 0"
              />
            </TresMesh>

            <!-- Regular blocks -->
            <TresMesh
              v-else
              :position="block.position"
              :scale="[2, 2, 2]"
              :receive-shadow="true"
              :cast-shadow="true"
              @click="block.isClickable ? toggleAudio : null"
            >
              <TresBoxGeometry :args="[1, 1, 1]" />
              <TresMeshStandardMaterial
                :map="getBlockTexture(block.type)"
                :roughness="0.9"
                :metalness="0.0"
              />
            </TresMesh>
          </template>
        </TresGroup>

      </TresCanvas>
    </ClientOnly>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import * as THREE from 'three'

// Use Nuxt color mode
const { $colorMode } = useNuxtApp()
const isDark = computed(() => $colorMode.value === 'dark')

// Load textures - using refs to initialize on client only
const dirtTexture = ref(null)
const stoneTexture = ref(null)
const grassSideTexture = ref(null)
const grassTopTexture = ref(null)
const grassBlockMaterials = ref([])

// Initialize textures on client side only
onMounted(() => {
  const textureLoader = new THREE.TextureLoader()
  dirtTexture.value = textureLoader.load('/textures/dirt.png')
  stoneTexture.value = textureLoader.load('/textures/stone.png')
  grassSideTexture.value = textureLoader.load('/textures/grass_block_side.png')
  grassTopTexture.value = textureLoader.load('/textures/grass_block_top.png')

  // Apply nearest neighbor filtering
  for (let tex of [dirtTexture.value, stoneTexture.value, grassSideTexture.value, grassTopTexture.value]) {
    tex.magFilter = THREE.NearestFilter
    tex.minFilter = THREE.NearestFilter
    tex.wrapS = THREE.RepeatWrapping
    tex.wrapT = THREE.RepeatWrapping
  }

  // Create grass block materials
  grassBlockMaterials.value = [
    new THREE.MeshStandardMaterial({ map: grassSideTexture.value, roughness: 0.9, metalness: 0.0 }),
    new THREE.MeshStandardMaterial({ map: grassSideTexture.value, roughness: 0.9, metalness: 0.0 }),
    new THREE.MeshStandardMaterial({ map: grassTopTexture.value, roughness: 0.9, metalness: 0.0 }),
    new THREE.MeshStandardMaterial({ map: dirtTexture.value, roughness: 0.9, metalness: 0.0 }),
    new THREE.MeshStandardMaterial({ map: grassSideTexture.value, roughness: 0.9, metalness: 0.0 }),
    new THREE.MeshStandardMaterial({ map: grassSideTexture.value, roughness: 0.9, metalness: 0.0 })
  ]
})

// Helper to get block texture
const getBlockTexture = (type) => {
  switch (type) {
    case 'dirt': return dirtTexture.value
    case 'stone': return stoneTexture.value
    default: return dirtTexture.value
  }
}

// Audio system
const audioContext = ref(null)
const audioElement = ref(null)
const analyser = ref(null)
const dataArray = ref(null)
const isPlaying = ref(false)
const bassLevel = ref(0)

// Toggle audio playback
const toggleAudio = () => {
  if (!audioElement.value) {
    // Create audio on first click
    audioElement.value = new Audio('/sounds/music.mp3') // Add your audio file here
    audioElement.value.loop = true

    // Create audio context and analyser
    audioContext.value = new (window.AudioContext || window.webkitAudioContext)()
    const source = audioContext.value.createMediaElementSource(audioElement.value)
    analyser.value = audioContext.value.createAnalyser()
    analyser.value.fftSize = 256

    source.connect(analyser.value)
    analyser.value.connect(audioContext.value.destination)

    const bufferLength = analyser.value.frequencyBinCount
    dataArray.value = new Uint8Array(bufferLength)
  }

  if (isPlaying.value) {
    audioElement.value.pause()
    isPlaying.value = false
  } else {
    audioElement.value.play()
    isPlaying.value = true
  }
}

// Custom block structure - BLUETOOTH SPEAKER with smaller blocks!
const customStructure = reactive([
  // Base layer - grass floor (7x7)
  { position: [-6, -6, -6], type: 'grass' },
  { position: [-4, -6, -6], type: 'grass' },
  { position: [-2, -6, -6], type: 'grass' },
  { position: [0, -6, -6], type: 'grass' },
  { position: [2, -6, -6], type: 'grass' },
  { position: [4, -6, -6], type: 'grass' },
  { position: [6, -6, -6], type: 'grass' },

  { position: [-6, -6, -4], type: 'grass' },
  { position: [-4, -6, -4], type: 'grass' },
  { position: [-2, -6, -4], type: 'grass' },
  { position: [0, -6, -4], type: 'grass' },
  { position: [2, -6, -4], type: 'grass' },
  { position: [4, -6, -4], type: 'grass' },
  { position: [6, -6, -4], type: 'grass' },

  { position: [-6, -6, -2], type: 'grass' },
  { position: [-4, -6, -2], type: 'grass' },
  { position: [-2, -6, -2], type: 'grass' },
  { position: [0, -6, -2], type: 'grass' },
  { position: [2, -6, -2], type: 'grass' },
  { position: [4, -6, -2], type: 'grass' },
  { position: [6, -6, -2], type: 'grass' },

  { position: [-6, -6, 0], type: 'grass' },
  { position: [-4, -6, 0], type: 'grass' },
  { position: [-2, -6, 0], type: 'grass' },
  { position: [0, -6, 0], type: 'grass' },
  { position: [2, -6, 0], type: 'grass' },
  { position: [4, -6, 0], type: 'grass' },
  { position: [6, -6, 0], type: 'grass' },

  { position: [-6, -6, 2], type: 'grass' },
  { position: [-4, -6, 2], type: 'grass' },
  { position: [-2, -6, 2], type: 'grass' },
  { position: [0, -6, 2], type: 'grass' },
  { position: [2, -6, 2], type: 'grass' },
  { position: [4, -6, 2], type: 'grass' },
  { position: [6, -6, 2], type: 'grass' },

  { position: [-6, -6, 4], type: 'grass' },
  { position: [-4, -6, 4], type: 'grass' },
  { position: [-2, -6, 4], type: 'grass' },
  { position: [0, -6, 4], type: 'grass' },
  { position: [2, -6, 4], type: 'grass' },
  { position: [4, -6, 4], type: 'grass' },
  { position: [6, -6, 4], type: 'grass' },

  { position: [-6, -6, 6], type: 'grass' },
  { position: [-4, -6, 6], type: 'grass' },
  { position: [-2, -6, 6], type: 'grass' },
  { position: [0, -6, 6], type: 'grass' },
  { position: [2, -6, 6], type: 'grass' },
  { position: [4, -6, 6], type: 'grass' },
  { position: [6, -6, 6], type: 'grass' },

  // SPEAKER BODY - Stone blocks (5 blocks wide, 10 blocks tall)
  // Bottom row
  { position: [-4, -4, 0], type: 'stone' },
  { position: [-2, -4, 0], type: 'stone' },
  { position: [0, -4, 0], type: 'stone' },
  { position: [2, -4, 0], type: 'stone' },
  { position: [4, -4, 0], type: 'stone' },

  // Row 2
  { position: [-4, -2, 0], type: 'stone' },
  { position: [-2, -2, 0], type: 'stone' },
  { position: [0, -2, 0], type: 'stone' },
  { position: [2, -2, 0], type: 'stone' },
  { position: [4, -2, 0], type: 'stone' },

  // Row 3 - Start of speaker cone
  { position: [-4, 0, 0], type: 'stone' },
  { position: [-2, 0, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [0, 0, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [2, 0, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [4, 0, 0], type: 'stone' },

  // Row 4 - Middle of speaker cone
  { position: [-4, 2, 0], type: 'stone' },
  { position: [-2, 2, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [0, 2, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [2, 2, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [4, 2, 0], type: 'stone' },

  // Row 5 - Top of speaker cone
  { position: [-4, 4, 0], type: 'stone' },
  { position: [-2, 4, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [0, 4, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [2, 4, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [4, 4, 0], type: 'stone' },

  // Row 6
  { position: [-4, 6, 0], type: 'stone' },
  { position: [-2, 6, 0], type: 'stone' },
  { position: [0, 6, 0], type: 'stone' },
  { position: [2, 6, 0], type: 'stone' },
  { position: [4, 6, 0], type: 'stone' },

  // Row 7 - Tweeter speaker (smaller)
  { position: [-4, 8, 0], type: 'stone' },
  { position: [-2, 8, 0], type: 'stone' },
  { position: [0, 8, 0], type: 'dirt', isSpeakerCone: true, animatedScale: [2, 2, 2] },
  { position: [2, 8, 0], type: 'stone' },
  { position: [4, 8, 0], type: 'stone' },

  // Row 8
  { position: [-4, 10, 0], type: 'stone' },
  { position: [-2, 10, 0], type: 'stone' },
  { position: [0, 10, 0], type: 'stone' },
  { position: [2, 10, 0], type: 'stone' },
  { position: [4, 10, 0], type: 'stone' },

  // Row 9 - Control buttons (clickable)
  { position: [-4, 12, 0], type: 'stone' },
  { position: [-2, 12, 0], type: 'dirt', isClickable: true, animatedScale: [2, 2, 2] },
  { position: [0, 12, 0], type: 'stone' },
  { position: [2, 12, 0], type: 'dirt', isClickable: true, animatedScale: [2, 2, 2] },
  { position: [4, 12, 0], type: 'stone' },

  // Top row
  { position: [-4, 14, 0], type: 'stone' },
  { position: [-2, 14, 0], type: 'stone' },
  { position: [0, 14, 0], type: 'stone' },
  { position: [2, 14, 0], type: 'stone' },
  { position: [4, 14, 0], type: 'stone' },
])

// Stars
const stars = reactive(Array.from({ length: 200 }, () => {
  const theta = Math.random() * Math.PI * 2
  const phi = Math.random() * Math.PI / 2.5 + Math.PI / 8
  const distance = 75 + Math.random() * 20
  const baseOpacity = 0.4 + Math.random() * 0.6

  return {
    position: [
      Math.sin(phi) * Math.cos(theta) * distance,
      Math.cos(phi) * distance,
      Math.sin(phi) * Math.sin(theta) * distance
    ],
    size: 0.08 + Math.random() * 0.25,
    baseOpacity: baseOpacity,
    currentOpacity: baseOpacity,
    twinkleSpeed: 0.5 + Math.random() * 2,
    twinklePhase: Math.random() * Math.PI * 2
  }
}))

// Renderer config
const gl = {
  clearColor: 0x1a1a1a,
  alpha: true,
  antialias: true
}

// Animation loop
onMounted(() => {
  let lastTime = 0

  const animate = () => {
    const currentTime = Date.now()
    const elapsed = currentTime / 1000
    const delta = (currentTime - lastTime) / 1000
    lastTime = currentTime

    // Animate star twinkling
    if (isDark.value) {
      stars.forEach(star => {
        const twinkle = Math.sin(elapsed * star.twinkleSpeed + star.twinklePhase)
        const twinkleIntensity = 0.3
        star.currentOpacity = star.baseOpacity + (twinkle * twinkleIntensity * star.baseOpacity)
        star.currentOpacity = Math.max(0.1, Math.min(1.0, star.currentOpacity))
      })
    }

    // Analyze audio frequencies for bass-reactive animation
    if (isPlaying.value && analyser.value && dataArray.value) {
      analyser.value.getByteFrequencyData(dataArray.value)

      // Get bass frequencies (first 8 bins - roughly 0-344 Hz at 44100 sample rate)
      let bassSum = 0
      for (let i = 0; i < 8; i++) {
        bassSum += dataArray.value[i]
      }
      const bassAvg = bassSum / 8

      // Normalize bass level (0-255 -> 0-1)
      bassLevel.value = bassAvg / 255

      // Update speaker cone block scales based on bass level
      customStructure.forEach(block => {
        if (block.isSpeakerCone) {
          const pulseScale = 2 + (bassLevel.value * 0.5) // Scale from 2 to 2.5
          block.animatedScale = [pulseScale, pulseScale, pulseScale]
        }
      })
    } else if (!isPlaying.value) {
      // Reset scales when not playing
      bassLevel.value = 0
      customStructure.forEach(block => {
        if (block.isSpeakerCone) {
          block.animatedScale = [2, 2, 2]
        }
      })
    }

    requestAnimationFrame(animate)
  }

  animate()
})
</script>

<style scoped>
.block-builder-container {
  width: 100%;
  height: 600px;
  position: relative;
  border-radius: 0.5rem;
  overflow: hidden;
}

.block-builder-container canvas {
  display: block;
  border-radius: 0.5rem;
}
</style>